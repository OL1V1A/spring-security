package com.lwj.springsecurity.controller;

import com.lwj.springsecurity.config.redis.RedisCacheService;
import com.lwj.springsecurity.entity.JwtUser;
import com.lwj.springsecurity.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/login")
    public String getUsers(){
        System.out.println("正在登陆");

        return "Hello Spring Security";
    }

    @RequestMapping("/json")
    public Map getMap(){
        Map map = new HashMap();
        map.put("a","java");
        map.put("b","c#");
        map.put("c","python");
        return map;
    }

    @RequestMapping("/get")
    public ModelAndView success(){
        return new ModelAndView(new RedirectView("http://www.baidu.com"));
    }

    @Autowired
    private RedisCacheService redisCacheService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/setRedis")
    public void redis(){
        LoginUser user = new LoginUser();
        user.setUsername("lwj");
        user.setPassword("qqq");
        redisTemplate.opsForValue().set("123","456");
        redisCacheService.set("user",user);
        System.out.println("数据存入redis");
    }

    @RequestMapping("/getRedis")
    public Map getRedis(){
        Map<String,Object> map = new HashMap<>();
        String num = (String) redisTemplate.opsForValue().get("123");
        LoginUser user = (LoginUser) redisCacheService.get("user");
        map.put("num",num);
        map.put("user",user);
        return map;
    }

}
