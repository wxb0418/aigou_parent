package cn.itsource.service.impl;

import cn.itsource.domain.ProductType;
import cn.itsource.mapper.ProductTypeMapper;
import cn.itsource.service.IProductTypeService;
import cn.itsource.utils.PageList;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    public List<ProductType> treeData() {
        //return productTypeMapper.treeData();
        return treeDataRecursion(0L);
    }

    private List<ProductType> treeDataRecursion(long l) {
        List<ProductType> children = getChildrenByPid(l);
        if(children == null || children.size()==0){
            return null;
        }

        for (ProductType child : children) {
            treeDataRecursion(child.getId());
        }
        return children;
    }

    private List<ProductType> getChildrenByPid(long pid) {
        Wrapper<ProductType> wrapper = new EntityWrapper<ProductType>();
        wrapper.eq("pid",pid);
        return productTypeMapper.selectList(wrapper);
    }
}
