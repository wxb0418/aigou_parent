package cn.itsource.common.redis.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "COMMON-PROVIDER",fallbackFactory = RedisClientFallBackFactory.class) //表示对哪一个服务进行处理
@RequestMapping("/common")
public interface RedisClient {
    @RequestMapping(value = "/redis",method = RequestMethod.POST)
    public void set(@RequestParam("key") String key,@RequestParam("value") String value);
    //这个{key}很关键，前台传参过来
    @RequestMapping(value = "/redis/{key}",method = RequestMethod.GET)
    public String get(@PathVariable("key") String key);

}
