package cn.itsource.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "cn.itsource.redis.client")
public class RedisApllication {
    public static void main(String[] args) {
        SpringApplication.run(RedisApllication.class);
    }
}
