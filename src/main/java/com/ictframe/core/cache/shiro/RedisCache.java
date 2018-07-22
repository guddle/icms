package com.ictframe.core.cache.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ictframe.core.Constants;
import com.ictframe.core.util.CacheUtil;
/**
 * @author jerry
 * @Date Jul 12, 2018
 * @param <K>
 * @param <V>
 */
public class RedisCache<K,V> implements Cache<K, V> {
	
	private final Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private String keyPrefix = Constants.SYSTEM_CACHE_NAMESPACE + "SHIRO-CACHE:";

	public RedisCache() {
	}
	
	public RedisCache(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}
	
	public String getKeyPrefix() {
		return keyPrefix;
	}

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	@Override
	public void clear() throws CacheException {
		logger.debug("删除所有Redis缓存");
		CacheUtil.getCache().delAll(this.keyPrefix + "*");
		
	}

	@Override
	public V get(K arg0) throws CacheException {
		logger.debug("根据key从Redis中获取对象 key [{}]",arg0);
        @SuppressWarnings("unchecked")
        V value = (V)CacheUtil.getCache().getFire(getKey(arg0));
        return value;
	}

	private String getKey(K arg0) {
		return this.keyPrefix + arg0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		  Set<Object> keys = CacheUtil.getCache().getAll(this.keyPrefix + "*");
	        if (CollectionUtils.isEmpty(keys)) {
	            return Collections.emptySet();
	        } else {
	            Set<K> newKeys = new HashSet<K>();
	            for (Object key : keys) {
	                newKeys.add((K)key);
	            }
	            return newKeys;
	        }
	}

	@Override
	public V put(K arg0, V arg1) throws CacheException {
		logger.debug("根据key从存储 key [{}]", arg0);
        CacheUtil.getCache().set(getKey(arg0), (Serializable)arg1);
        return arg1;
	}

	@Override
	public V remove(K arg0) throws CacheException {
		logger.debug("从redis中删除 key [{}]", arg0);
        V previous = get(arg0)==null?null:get(arg0);
        CacheUtil.getCache().del(getKey(arg0));
        return previous;
	}

	@Override
	public int size() {
		return CacheUtil.getCache().getAll(this.keyPrefix + "*").size();
	}

	@Override
	public Collection<V> values() {
		Set<Object> keys = CacheUtil.getCache().getAll(this.keyPrefix + "*");
        if (!CollectionUtils.isEmpty(keys)) {
            List<V> values = new ArrayList<V>(keys.size());
            for (Object key : keys) {
                @SuppressWarnings("unchecked")
                V value = get((K)key);
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
