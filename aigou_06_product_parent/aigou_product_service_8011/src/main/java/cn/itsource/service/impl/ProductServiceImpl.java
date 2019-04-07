package cn.itsource.service.impl;

import cn.itsource.domain.Product;
import cn.itsource.domain.ProductExt;
import cn.itsource.mapper.ProductExtMapper;
import cn.itsource.mapper.ProductMapper;
import cn.itsource.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author wxb
 * @since 2019-04-06
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductExtMapper productExtMapper;

    @Override
    public boolean insert(Product entity) {
        //添加创建时间
        entity.setCreateTime(new Date().getTime());
        //先添加主表
        boolean insert = super.insert(entity);
        //再添加关联的表
        ProductExt productExt = entity.getProductExt();
        productExt.setProductId(entity.getId());
        productExtMapper.insert(productExt);
        return insert;
    }

    @Override
    public boolean updateById(Product entity) {
        entity.setUpdateTime(new Date().getTime());
        //先更新主表
        boolean b = super.updateById(entity);
        //再更新关联的表
        ProductExt productExt = entity.getProductExt();
        productExt.setProductId(entity.getId());
        productExtMapper.updateById(productExt);
        return b;
    }
}
