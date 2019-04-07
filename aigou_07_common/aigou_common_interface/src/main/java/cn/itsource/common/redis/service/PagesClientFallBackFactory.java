package cn.itsource.common.redis.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class PagesClientFallBackFactory implements FallbackFactory<PagesClient> {
    @Override
    public PagesClient create(Throwable throwable) {
       return new PagesClient() {
           @Override
           public void creatStaticPage(Map<String, Object> map) {

           }
       };
    }
}
