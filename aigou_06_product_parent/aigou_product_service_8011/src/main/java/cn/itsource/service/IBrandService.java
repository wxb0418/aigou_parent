package cn.itsource.service;

import cn.itsource.domain.Brand;
import cn.itsource.query.BrandQuery;
import cn.itsource.utils.PageList;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author wxb
 * @since 2019-03-30
 */
public interface IBrandService extends IService<Brand> {


    PageList<Brand> queryPage(BrandQuery query);
}
