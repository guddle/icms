package com.ictframe.core.cache.shiro;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
/**
 * @author jerry
 * @Date Jul 13, 2018
 */
public class RedisCacheManager implements CacheManager {
	
	private final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
//    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	private RedisTemplate<String, Object> redisTemplate;
	
	@SuppressWarnings({"rawtypes","unchecked"})
	public RedisCacheManager(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug("获取名称为: {} 的RedisCache实例", name);

//		Cache<K, V> cache_n = caches.get(arg0);
//
//        if (cache_n == null) {
//            // create a new cache instance
//        RedisCache<K, V> cache = new RedisCache<K, V>(name,)
//            // add it to the cache collection
//            caches.put(arg0, cache);
//        }
        return new RedisCache<>(name, redisTemplate);
	}

}
