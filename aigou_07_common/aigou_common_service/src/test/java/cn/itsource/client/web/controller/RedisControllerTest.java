package cn.itsource.client.web.controller;

import cn.itsource.client.RedisApplication;
import cn.itsource.common.redis.service.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisControllerTest {
    @Autowired
    private RedisClient redisClient;
    @Test
    public void get() {
        String treedata = redisClient.get("tyuyuy");
        System.out.println(treedata);
    }
}