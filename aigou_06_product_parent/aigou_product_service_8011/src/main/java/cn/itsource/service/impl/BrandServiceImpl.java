package cn.itsource.service.impl;

import cn.itsource.domain.Brand;
import cn.itsource.mapper.BrandMapper;
import cn.itsource.query.BrandQuery;
import cn.itsource.service.IBrandService;
import cn.itsource.utils.PageList;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 品牌信息 服务实现类
 * </p>
 *
 * @author wxb
 * @since 2019-03-30
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IBrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageList<Brand> queryPage(BrandQuery query) {
        //分页查询  查询总条数和每页的数据
        long total = 0;
        total =  brandMapper.queryPageCount(query);
        if(total == 0){
            return new PageList<Brand>();
        }else {
            List<Brand> list = brandMapper.queryPageList(query);
            return new PageList<Brand>(total,list);
        }
    }
}
