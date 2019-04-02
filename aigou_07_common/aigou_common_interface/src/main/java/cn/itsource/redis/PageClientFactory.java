package cn.itsource.redis;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PageClientFactory implements FallbackFactory<PageClient> {
    @Override
    public PageClient create(Throwable throwable) {
        return null;
    }

    /*@Override
    public PageClient create(Throwable throwable) {
        return new PageClient() {
            @Override
            public void set(String key, String value) {

            }

            @Override
            public String get(String key) {
                return null;
            }

            @Override
            public void getPageStatic(Map<String, Object> map) {

            }
        };
    }*/
}