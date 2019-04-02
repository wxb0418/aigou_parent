package cn.itsource.redis;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/common")
public interface PageClient {

    /*@RequestMapping(value = "/page", method = RequestMethod.POST)
    void set(@RequestParam("key") String key, @RequestParam("value") String value);

    @RequestMapping(value = "/page/{key}", method = RequestMethod.GET)
    String get(@PathVariable("key") String key);

    *//**
     * 生成模板:
     *  根据给定的数据,和指定的模板,最终生成一个html页面;
     * @param map
     *//*
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    void getPageStatic(@RequestBody Map<String,Object> map);*/
}