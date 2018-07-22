package com.ictframe.core.cache.shiro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ictframe.core.Constants;
/**
 * @author jerry
 * @Date Jul 13, 2018
 */
public class RedisCacheManager implements CacheManager {
	
	private final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);
	@SuppressWarnings("rawtypes")
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	private String keyPrefix = Constants.SYSTEM_CACHE_NAMESPACE + "SHIRO-CACHE:";
	
	
	
	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}



	@Override
	@SuppressWarnings("unchecked")
	public <K, V> Cache<K, V> getCache(String arg0) throws CacheException {
		logger.debug("获取名称为: {} 的RedisCache实例", arg0);

		Cache<K, V> cache_n = caches.get(arg0);

        if (cache_n == null) {
            // create a new cache instance
            RedisCache<K, V> cache = new RedisCache<K, V>(keyPrefix);
            // add it to the cache collection
            caches.put(arg0, cache);
        }
        return cache_n;
	}

}
