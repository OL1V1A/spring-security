package com.lwj.springsecurity.test;

import com.lwj.springsecurity.config.redis.RedisCacheService;
import com.lwj.springsecurity.entity.LoginUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest()
public class testRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisCacheService redisCacheService;

    @Test
    public void test1(){
        redisTemplate.opsForValue().set("aaa","111");
        Assert.assertEquals("111",redisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void test2(){
        LoginUser user = new LoginUser();
        user.setUsername("lwj");
        user.setPassword("qqqqqq");
        redisTemplate.opsForValue().set(user.getUsername(),user);
        LoginUser result = (LoginUser) redisTemplate.opsForValue().get("lwj");
//        JSONObject
        System.out.println(result.getUsername()+ "--->" + result.getPassword());
    }

    @Test
    public void test3(){
        redisCacheService.set("uuu","www");
        Assert.assertEquals("www",redisCacheService.get("uuu"));
    }

    @Test
    public void test4(){
        LoginUser user = new LoginUser();
        user.setUsername("olivia");
        user.setPassword("password");
        redisTemplate.opsForValue().set(user.getUsername(),user);
        LoginUser result = (LoginUser) redisTemplate.opsForValue().get(user.getUsername());
        System.out.println(result.getUsername() + "--->" + result.getPassword());
    }
}
