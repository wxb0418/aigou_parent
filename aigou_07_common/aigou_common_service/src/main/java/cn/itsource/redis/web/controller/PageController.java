package cn.itsource.redis.web.controller;

import cn.itsource.redis.PageClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/common")
public class PageController implements PageClient {
    /*@Override
    public void set(String key, String value) {

    }

    @Override
    public String get(String key) {
        return null;
    }
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @Override
    public void getPageStatic(@RequestBody Map<String, Object> map) {
        Object model = map.get(GlobalConstants.PAGE_MODE);
        String templateFilePathAndName = (String) map.get(GlobalConstants.PAGE_TEMPLATE);
        String targetFilePathAndName  = (String) map.get(GlobalConstants.PAGE_TEMPLATE_HTML);
    }*/
}
