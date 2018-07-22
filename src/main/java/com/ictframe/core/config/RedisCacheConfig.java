package com.ictframe.core.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
@EnableCaching //开启缓存支持
public class RedisCacheConfig extends CachingConfigurerSupport{

	 @Bean
     public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
          RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
          return  redisCacheManager;
     }
}
