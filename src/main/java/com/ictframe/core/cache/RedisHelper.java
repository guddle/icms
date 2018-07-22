package com.ictframe.core.cache;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.ictframe.core.util.CacheUtil;
import com.ictframe.core.util.PropertiesUtil;

/**
 * 
 * @author jerry
 * @Date Jul 16, 2018
 */
public class RedisHelper implements CacheManager {

	private RedisSerializer<String> keySerializer;
	private RedisSerializer<Object> valueSerializer;
	private RedisTemplate<Serializable, Serializable> redisTemplate;
	private final Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");

	public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		return redisTemplate;
	}

	@SuppressWarnings("unchecked")
	public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
		this.redisTemplate = redisTemplate;
		this.keySerializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
		this.valueSerializer = (RedisSerializer<Object>) redisTemplate.getValueSerializer();
		CacheUtil.setCacheManager(this);
	}

	@Override
	public Object get(String key) {
		return redisTemplate.boundValueOps(key).get();
	}

	@Override
	public Set<Object> getAll(String pattern) {
		Set<Object> values = new HashSet<Object>();
		Set<Serializable> keys = redisTemplate.keys(pattern);
		for (Serializable key : keys) {
			values.add(redisTemplate.opsForValue().get(key));
		}
		return values;
	}

	@Override
	public void set(String key, Serializable value, int seconds) {
		redisTemplate.boundValueOps(key).set(value);
		expire(key, seconds);
	}

	@Override
	public void set(String key, Serializable value) {
		redisTemplate.boundValueOps(key).set(value);
		expire(key, EXPIRE);
	}

	@Override
	public Boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}

	@Override
	public void del(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void delAll(String pattern) {
		redisTemplate.delete(redisTemplate.keys(pattern));
	}

	@Override
	public String type(String key) {
		return redisTemplate.type(key).getClass().getName();
	}

	@Override
	public Boolean expire(String key, int seconds) {
		return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
	}

	/**
	 * 在某个时间点失效
	 */
	@Override
	public Boolean expireAt(String key, long unixTime) {
		return redisTemplate.expireAt(key, new Date(unixTime));
	}

	@Override
	public Long ttl(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	@Override
	public Object getSet(String key, Serializable value) {
		return redisTemplate.boundValueOps(key).getAndSet(value);
	}

	@Override
	public boolean lock(String key) {
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection redisConnection = null;
		try {
			redisConnection = RedisConnectionUtils.getConnection(factory);
			if (redisConnection == null) {
				return redisTemplate.boundValueOps(key).setIfAbsent("0");
			}
			return redisConnection.setNX(keySerializer.serialize(key), valueSerializer.serialize("0"));
		} finally {
			RedisConnectionUtils.releaseConnection(redisConnection, factory);
		}
	}

	@Override
	public void unlock(String key) {
		redisTemplate.delete(key);
	}

	@Override
	public void hset(String key, Serializable field, Serializable value) {
		redisTemplate.boundHashOps(key).put(field, value);
	}

	@Override
	public Object hget(String key, Serializable field) {
		return redisTemplate.boundHashOps(key).get(field);
	}

	@Override
	public void hdel(String key, Serializable field) {
		redisTemplate.boundHashOps(key).delete(field);
	}

	@Override
	public boolean setnx(String key, Serializable value) {
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection redisConnection = null;
		try {
			redisConnection = RedisConnectionUtils.getConnection(factory);
			if (redisConnection == null) {
				return redisTemplate.boundValueOps(key).setIfAbsent(value);
			}
			return redisConnection.setNX(keySerializer.serialize(key), valueSerializer.serialize(value));
		} finally {
			RedisConnectionUtils.releaseConnection(redisConnection, factory);
		}
	}

	@Override
	public Long incr(String key) {
		return redisTemplate.boundValueOps(key).increment(1L);
	}

	@Override
	public void setrange(String key, long offset, String value) {
		redisTemplate.boundValueOps(key).set(value, offset);
	}

	@Override
	public String getrange(String key, long startOffset, long endOffset) {
		return redisTemplate.boundValueOps(key).get(startOffset, endOffset);
	}

	@Override
	public Object get(String key, Integer expire) {
		expire(key, expire);
		return redisTemplate.boundValueOps(key).get();
	}

	@Override
	public Object getFire(String key) {
		expire(key, EXPIRE);
		return redisTemplate.boundValueOps(key).get();
	}

	@Override
	public Set<Object> getAll(String pattern, Integer expire) {
		Set<Object> values = new HashSet<Object>();
        Set<Serializable> keys = redisTemplate.keys(pattern);
        for (Serializable key : keys) {
        	expire(key.toString(), expire);
            values.add(redisTemplate.opsForValue().get(key));
        }
        return values;
	}

}
