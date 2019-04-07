package cn.itsource.service.impl;

import cn.itsource.domain.Brand;
import cn.itsource.mapper.BrandMapper;
import cn.itsource.query.BrandQuery;
import cn.itsource.service.IBrandService;
import cn.itsource.utils.LetterUtil;
import cn.itsource.utils.PageList;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    /**
     * 重写添加方法，添加创建时间和首字符
     * @param entity
     * @return
     */
    @Override
    public boolean insert(Brand entity) {
        entity.setCreateTime(new Date().getTime());
        entity.setFirstLetter(LetterUtil.getFirstLetter(entity.getName()));
        return super.insert(entity);
    }

    /**
     * 重写更新方法，更新时间和首字符
     * @param entity
     * @return
     */
    @Override
    public boolean updateById(Brand entity) {
        entity.setUpdateTime(new Date().getTime());
        entity.setFirstLetter(LetterUtil.getFirstLetter(entity.getName()));
        return super.updateById(entity);
    }
}
