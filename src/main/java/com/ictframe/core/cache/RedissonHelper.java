//package com.ictframe.core.cache;
//
//import java.io.Serializable;
//import java.util.Set;
//
//import org.redisson.api.RedissonClient;
//
//import com.ictframe.core.util.PropertiesUtil;
//
//public class RedissonHelper implements CacheManager {
//	
//	private RedissonClient redissonClient;
//    private final Integer EXPIRE = PropertiesUtil.getInt("redis.expiration");
//
//    public void setClient(Client Client) {
//        this.redissonClient = Client.getRedissonClient();
//        CacheUtil.setLockManager(this);
//    }
//    
//	@Override
//	public Object get(String key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Set<Object> getAll(String pattern) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void set(String key, Serializable value, int seconds) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void set(String key, Serializable value) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public Boolean exists(String key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void del(String key) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void delAll(String pattern) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public String type(String key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Boolean expire(String key, int seconds) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Boolean expireAt(String key, long unixTime) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Long ttl(String key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object getSet(String key, Serializable value) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean lock(String key) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void unlock(String key) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void hset(String key, Serializable field, Serializable value) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public Object hget(String key, Serializable field) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void hdel(String key, Serializable field) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean setnx(String key, Serializable value) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public Long incr(String key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void setrange(String key, long offset, String value) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public String getrange(String key, long startOffset, long endOffset) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object get(String key, Integer expire) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Object getFire(String key) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Set<Object> getAll(String pattern, Integer expire) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
