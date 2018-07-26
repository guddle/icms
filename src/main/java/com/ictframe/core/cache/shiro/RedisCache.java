package com.ictframe.core.cache.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.ictframe.core.Constants;
/**
 * Redis 的Shiro缓存实现
 * @author jerry
 * @Date Jul 12, 2018
 * @param <K>
 * @param <V>
 */
public class RedisCache<K,V> implements Cache<K, V> {
	
	private final static Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private RedisTemplate<K, V> redisTemplate;
	private final static String keyPrefix = "SHIRO-CACHE:";
	private String cacheKey;
	private long globExpire = 30;
	
	@SuppressWarnings({"rawtypes","unchecked"})
	public RedisCache(final String name, final RedisTemplate redisTemplate) {
		this.cacheKey = Constants.SYSTEM_CACHE_NAMESPACE + keyPrefix + name + ":";
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void clear() throws CacheException {
		logger.debug("删除所有Redis缓存");
		redisTemplate.delete(keys());
	}

	@Override
	public V get(K key) throws CacheException {
		logger.debug("Shiro从缓存中获取数据KEY值[{}]",key);
		redisTemplate.boundValueOps(getCacheKey(key)).expire(globExpire, TimeUnit.MINUTES);
        return redisTemplate.boundValueOps(getCacheKey(key)).get();
	}

	@SuppressWarnings("unchecked")
	private K getCacheKey(Object key) {
		return (K)(this.cacheKey + key);
	}

	@Override
	public Set<K> keys() {
		  return redisTemplate.keys(getCacheKey("*"));
	}

	@Override
	public V put(K key, V value) throws CacheException {
		logger.debug("根据key从存储 key [{}]", key);
		V old = get(key);
		redisTemplate.boundValueOps(getCacheKey(key)).set(value);
		return old;
	}

	@Override
	public V remove(K key) throws CacheException {
		logger.debug("从redis中删除 key [{}]", key);
		V old = get(key);
		redisTemplate.delete(getCacheKey(key));
		return old;
	}

	@Override
	public int size() {
		return keys().size();
	}

	@Override
	public Collection<V> values() {
		Set<K> keys = keys();
        if (!CollectionUtils.isEmpty(keys)) {
            List<V> values = new ArrayList<V>(keys.size());
            for (K key : keys) {
                V value = get(key);
                if (value != null) {
                    values.add(value);
                }
            }
            return Collections.unmodifiableList(values);
        } else {
            return Collections.emptyList();
        }
	}

	
}
