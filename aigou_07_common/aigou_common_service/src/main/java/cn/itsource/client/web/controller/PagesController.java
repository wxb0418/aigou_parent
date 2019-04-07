package cn.itsource.client.web.controller;

import cn.itsource.client.utils.VelocityUtils;
import cn.itsource.common.redis.service.PagesClient;
import cn.itsource.constants.GlobalConstants;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/common")
public class PagesController implements PagesClient {


    @Override
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public void creatStaticPage(@RequestBody Map<String, Object> map) {
        //获取模板
        Object page_mode = map.get(GlobalConstants.PAGE_MODE);
        //获取模板路径
        String page_template = (String) map.get(GlobalConstants.PAGE_TEMPLATE);
        //获取静态页面路径
        String page_template_html = (String) map.get(GlobalConstants.PAGE_TEMPLATE_HTML);
        System.out.println(page_mode);
        System.out.println(page_template);
        System.out.println(page_template_html);
        VelocityUtils.staticByTemplate(page_mode,page_template,page_template_html);

    }
}
