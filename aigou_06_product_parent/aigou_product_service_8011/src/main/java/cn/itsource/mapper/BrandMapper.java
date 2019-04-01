package cn.itsource.mapper;

import cn.itsource.domain.Brand;
import cn.itsource.query.BrandQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 品牌信息 Mapper 接口
 * </p>
 *
 * @author wxb
 * @since 2019-03-30
 */

public interface BrandMapper extends BaseMapper<Brand> {

    /**
     * 分页条件数据
     * @param query
     * @return
     */
    List<Brand> queryPageList(BrandQuery query);


    /**
     * 分页条件查询:总的条数
     * @param query
     * @return
     */
    long queryPageCount( BrandQuery query);
}
