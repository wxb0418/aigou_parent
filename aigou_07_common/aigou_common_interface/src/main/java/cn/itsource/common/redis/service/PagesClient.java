package cn.itsource.common.redis.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "COMMON-PROVIDER",fallbackFactory = PagesClientFallBackFactory.class) //表示对哪一个服务进行处理
@RequestMapping("/common")
public interface PagesClient {
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    void creatStaticPage(@RequestBody Map<String,Object> map);
}
