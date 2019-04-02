package cn.itsource.service.impl;

import cn.itsource.constants.GlobalConstants;
import cn.itsource.domain.ProductType;
import cn.itsource.mapper.ProductTypeMapper;
import cn.itsource.redis.RedisClient;
import cn.itsource.service.IProductTypeService;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<ProductType> treeData() {
        //return productTypeMapper.treeData();
        //获取所有的父类
        //return treeDataRecursion(0L);
        String jsonArrStr = redisClient.get(GlobalConstants.REDIS_PRODUCTTYPE_KEY);
        if(StringUtils.isEmpty(jsonArrStr)){
            //没有就从数据库获取，并存入redis，并且返回
            List<ProductType> productTypes = treeDataLoop();
            jsonArrStr = JSONArray.toJSONString(productTypes);
            redisClient.set(GlobalConstants.REDIS_PRODUCTTYPE_KEY,jsonArrStr);
            return productTypes;
        }else {
            //有的话就直接返回
            return JSONArray.parseArray(jsonArrStr,ProductType.class);
        }
    }

    /**
     * 递归：自己调用自己，效率非常低，每次都要发送sql
     * @param l
     * @return
     */
    private List<ProductType> treeDataRecursion(long l) {
        //调用获取子类的方法，获取子类集合
        List<ProductType> children = getChildrenByPid(l);
        //出口：如果子类是null，或者集合的长度小于等于0，说明他就没有子类了，返回null
        if(children == null || children.size()==0){
            return null;
        }
        //如果子类不是null，遍历children
        for (ProductType child : children) {
            //递归，自己调用自己
            treeDataRecursion(child.getId());
        }
        return children;
    }

    /**
     * 循环方案
     * 查询所有的类型--只需要发送一次sql就可以了
     *  遍历所有的类型
     *      如果没有父级就是一级菜单，直接放入列表中
     *      如果有父级，就把自己当做一个儿子
     * @param l
     * @return
     */
    public List<ProductType> treeDataLoop(){

        List<ProductType> result = new ArrayList<>();
        //拿到所有的产品类型
        List<ProductType> productTypes = productTypeMapper.selectList(null);
        Map<Long,ProductType> map = new HashMap<>();
        //遍历集合，将所有的产品类型和id存入map中
        for (ProductType productType : productTypes) {
            map.put(productType.getId(),productType);
        }
        for (ProductType productType : productTypes) {
            Long pid = productType.getPid();
            //pid的值是0，说明就是父级
            if(pid.longValue() == 0){
                result.add(productType);
            }else {
                //如不是0，则用map通过pid拿到父级
                ProductType parent = map.get(pid);
                parent.getChildrenList().add(productType);
            }
        }
        return  result;
    }
    //通过pid获取所有的子类
    private List<ProductType> getChildrenByPid(long pid) {
        Wrapper<ProductType> wrapper = new EntityWrapper<ProductType>();
        wrapper.eq("pid",pid);
        return productTypeMapper.selectList(wrapper);
    }
}
