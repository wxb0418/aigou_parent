package cn.itsource.service.impl;

import cn.itsource.common.redis.service.PagesClient;
import cn.itsource.common.redis.service.RedisClient;
import cn.itsource.constants.GlobalConstants;
import cn.itsource.domain.ProductType;
import cn.itsource.mapper.ProductTypeMapper;
import cn.itsource.service.IProductTypeService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author wxb
 * @since 2019-03-31
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private RedisClient redisClient;
    @Autowired
    private PagesClient pagesClient;

    /**
     * @return
     */
    public List<ProductType> treeData() {
        //递归：获取所有的父类，性能太差
        //return treeDataRecursion(0L);
        //调用循环的方法，只发一条sql
        //return treeDataLoop();
        //通过key获取value
        String jsonStrArr = redisClient.get(GlobalConstants.REDIS_PRODUCTTYPE_KEY);
        if(StringUtils.isEmpty(jsonStrArr)){
            List<ProductType> productTypes = treeDataLoop();
            String jsonString = JSON.toJSONString(productTypes);
            redisClient.set(GlobalConstants.REDIS_PRODUCTTYPE_KEY,jsonString);
            System.out.println("===========db=================");
            return productTypes;
        }else {
            System.out.println("=============cache==================");
            return JSON.parseArray(jsonStrArr,ProductType.class);
        }

    }

    /**
     * 递归：自己调用自己，效率非常低，每次都要发送sql
     *
     * @param l
     * @return
     */
    private List<ProductType> treeDataRecursion(long l) {
        //调用获取子类的方法，获取子类集合
        List<ProductType> children = getChildrenByPid(l);
        //出口：如果子类是null，或者集合的长度小于等于0，说明他就没有子类了，返回null
        if (children == null || children.size() == 0) {
            return null;
        }
        //如果子类不是null，遍历children
        for (ProductType child : children) {
            //递归，自己调用自己
            List<ProductType> childrenList = treeDataRecursion(child.getId());
            //再把自己的儿子设置给自己
            child.setChildren(childrenList);
        }
        return children;
    }

    /**
     * 循环方案
     * 查询所有的类型--只需要发送一次sql就可以了
     * 遍历所有的类型
     * 如果没有父级就是一级菜单，直接放入列表中
     * 如果有父级，就把自己当做一个儿子
     *
     * @param
     * @return
     */
    public List<ProductType> treeDataLoop() {

        List<ProductType> result = new ArrayList<>();
        //拿到所有的产品类型
        List<ProductType> productTypes = productTypeMapper.selectList(null);
        Map<Long, ProductType> map = new HashMap<>();
        //遍历集合，将所有的产品类型和id存入map中
        for (ProductType productType : productTypes) {
            map.put(productType.getId(), productType);
        }
        for (ProductType productType : productTypes) {
            Long pid = productType.getPid();
            //pid的值是0，说明就是父级
            if (pid.longValue() == 0) {
                result.add(productType);
            } else {
                //如不是0，则用map通过pid拿到父级
                ProductType parent = map.get(pid);
                parent.getChildren().add(productType);
            }
        }
        return result;
    }

    //通过pid获取所有的子类
    private List<ProductType> getChildrenByPid(long pid) {
        Wrapper<ProductType> wrapper = new EntityWrapper<ProductType>();
        wrapper.eq("pid", pid);
        return productTypeMapper.selectList(wrapper);
    }

    /**
     * 删除数据
     *  1.先把数据从数据库删除，然后把新的数据存入redis
     */
    @Override
    public boolean deleteById(Serializable id) {
        //根据id删除数据库的数据
        boolean b = super.deleteById(id);
        //查询数据并重新放入redis
        //List<ProductType> productTypes = treeDataLoop();
        //redisClient.set(GlobalConstants.REDIS_PRODUCTTYPE_KEY,JSON.toJSONString(productTypes));
        setDataToRedis();
        //生成静态页面
        creatStaticPageHtml();
        return b;
    }

    /**
     * 更新数据
     *  1.先跟上数据库中的数据
     *  2.更新之后查询，然后存入redis
     */
    @Override
    public boolean updateById(ProductType entity) {
        boolean b = super.updateById(entity);
        setDataToRedis();
        //生成静态页面
        creatStaticPageHtml();
        return b;
    }
    /**
     * 添加数据
     */
    @Override
    public boolean insert(ProductType entity) {
        boolean b = super.insert(entity);
        //存入redis
        setDataToRedis();
        //生成静态页面
        creatStaticPageHtml();
        return b;
    }

    /**
     * 查询数据库，并存入redis的方法
     */
    public void setDataToRedis(){
        List<ProductType> productTypes = treeDataLoop();
        redisClient.set(GlobalConstants.REDIS_PRODUCTTYPE_KEY,JSON.toJSONString(productTypes));
    }

    /**
     * 页面静态化
     */
    public void creatStaticPageHtml(){
        Map<String,Object> productMapHtml = new HashMap<>();

       /*
       封装三个参数，生成产品类型页面
            1.数据
            2.模板
            3.生成页面
        */
        productMapHtml.put(GlobalConstants.PAGE_MODE,treeDataLoop());
        productMapHtml.put(GlobalConstants.PAGE_TEMPLATE,"D:\\aigou\\aigou_parent\\aigou_07_common\\aigou_common_interface\\src\\main\\resources\\template\\product.type.vm");
        productMapHtml.put(GlobalConstants.PAGE_TEMPLATE_HTML,"D:\\aigou\\aigou_parent\\aigou_07_common\\aigou_common_interface\\src\\main\\resources\\template\\product.type.vm.html");
        pagesClient.creatStaticPage(productMapHtml);
        /*
        生成主页面
         */
        Map<String,Object> homeMapHtml = new HashMap<>();
        Map<String,Object> modelMap = new HashMap<>();
        modelMap.put(GlobalConstants.STATICROOT_KEY,"D:\\aigou\\aigou_parent\\aigou_07_common\\aigou_common_interface\\src\\main\\resources\\");
        homeMapHtml.put(GlobalConstants.PAGE_MODE,modelMap);
        homeMapHtml.put(GlobalConstants.PAGE_TEMPLATE,"D:\\aigou\\aigou_parent\\aigou_07_common\\aigou_common_interface\\src\\main\\resources\\template\\home.vm");
        homeMapHtml.put(GlobalConstants.PAGE_TEMPLATE_HTML,"D:\\aigou\\aigou_parent\\aigou_07_common\\aigou_common_interface\\src\\main\\resources\\template\\home.vm.html");
        pagesClient.creatStaticPage(homeMapHtml);
    }


}
