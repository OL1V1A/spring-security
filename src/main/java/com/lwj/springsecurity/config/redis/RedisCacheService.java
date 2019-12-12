package com.lwj.springsecurity.config.redis;

import com.lwj.springsecurity.entity.LoginUser;
import com.lwj.springsecurity.utils.KryoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheService {

    @Autowired
    RedisTemplate<String,Object> stringRedisTemplate;

    public Collection<String> keys(String pattern){
        return stringRedisTemplate.keys(pattern);
    }

    public void delete(String key){
        stringRedisTemplate.delete(key);
    }

    public void delete(Collection<String> keys){
        stringRedisTemplate.delete(keys);
    }

    public boolean hashKey(String key){
        return stringRedisTemplate.hasKey(key);
    }

    public Object get(String key){
        Object value = stringRedisTemplate.opsForValue().get(key);
        if (value != null){
            byte[] bytes = (byte[]) value;
            value = KryoUtils.deserilize(bytes);
        }
        return value;
    }

    public void set(String key,Object obj){
        if (obj == null){
            return;
        }
        stringRedisTemplate.opsForValue().set(key,KryoUtils.serialize(obj));
    }

    public String getString(String key){
        Object value = stringRedisTemplate.opsForValue().get(key);
        String valueReturn = "";
        if (value != null){
            byte[] bytes = (byte[]) value;
            valueReturn = KryoUtils.deserilize(bytes).toString();
        }
        return valueReturn;
    }

    public void setKetByTime(String key, Object obj, Long timeout, TimeUnit unit){
        if (obj == null){
            return;
        }
        if (timeout != null){
            stringRedisTemplate.opsForValue().set(key,KryoUtils.serialize(obj),timeout,unit);
        }else{
            stringRedisTemplate.opsForValue().set(key,KryoUtils.serialize(obj));
        }
    }

    public static void main(String[] args) {
        String value = "AQBjb20ubHdqLnNwcmluZ3NlY3VyaXR5LmVudGl0eS5Mb2dpblVzZfIBAXFx8QFsd+o=";
        byte[] bytes = value.getBytes();
        Object value1 = KryoUtils.deserilize(bytes);
        LoginUser user = (LoginUser) value1;
        System.out.println(user);
    }
}
