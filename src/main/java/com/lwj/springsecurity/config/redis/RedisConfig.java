package com.lwj.springsecurity.config.redis;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import com.lwj.springsecurity.utils.KryoSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

@Configuration
//@EnableCaching
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.database}")
    private int database;
    @Value("${spring.redis.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.min-idle}")
    private int minIdle;
    @Value("${spring.redis.max-active}")
    private int maxActive;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setMaxTotal(100);
        config.setMaxWaitMillis(10000);
        return config;
    }

//    @Bean
//    public RedisConnectionFactory jedisConnectionFactory(){
//
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPassword(password);
//        redisStandaloneConfiguration.setDatabase(database);
//        redisStandaloneConfiguration.setPort(port);
//
//        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb=
//                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
//        jpcb.poolConfig(jedisPoolConfig());
//        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
//
//        return new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration);
//    }

//    @Bean
//    public RedisTemplate<String,Object> redisTemplate(){
//        RedisTemplate template = new RedisTemplate();
//        template.setConnectionFactory(jedisConnectionFactory());
//        template.setKeySerializer(fastJsonRedisSerializer());
//        template.setValueSerializer(fastJsonRedisSerializer());
//        return template;
//    }

    @Bean
    public FastJsonRedisSerializer<?> fastJsonRedisSerializer(){
        return new FastJsonRedisSerializer<>(Object.class);
    }






    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
//        template.setValueSerializer(new JdkSerializationRedisSerializer());
//        template.setValueSerializer(new KryoSerializer());
        template.setDefaultSerializer(new KryoSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }


}
