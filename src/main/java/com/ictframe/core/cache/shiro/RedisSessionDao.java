package com.ictframe.core.cache.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import com.ictframe.core.Constants;
import com.ictframe.core.util.PropertiesUtil;
import com.ictframe.core.util.SerializeUtil;

public class RedisSessionDao extends AbstractSessionDAO{
	
	private static final int EXPIRE_TIME = 600;
	@Autowired
	private RedisTemplate<Serializable, Serializable> redisTemplate;

	@Override
	public void update(Session session) throws UnknownSessionException {
		saveSession(session);
	}

	/**
	 * 
	 * @author jerry
	 * @Date Jul 13, 2018
	 * @param id session's id
	 */
	public void delete(Serializable id) {
		if (id != null) {
			byte[] sessionKey = buildRedisSessionKey(id);
			RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
			RedisConnection conn = null;
			try {
				conn = RedisConnectionUtils.getConnection(factory);
				conn.del(sessionKey);
			} finally {
				RedisConnectionUtils.releaseConnection(conn, factory);
			}
		}
	}
	
	private byte[] buildRedisSessionKey(Serializable id) {
		return (Constants.REDIS_SHIRO_SESSION + id).getBytes();
	}

	@Override
	public void delete(Session session) {
		if (session != null) {
			Serializable id = session.getId();
			if (id != null) {
				RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
				RedisConnection conn = null;
				try {
					conn = RedisConnectionUtils.getConnection(factory);
					conn.del(buildRedisSessionKey(id));
				} finally {
					RedisConnectionUtils.releaseConnection(conn, factory);
				}
			}
		}
		
	}

	@Override
	public Collection<Session> getActiveSessions() {
		List<Session> list = new ArrayList<Session>();
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection conn = null;
		try {
			conn = RedisConnectionUtils.getConnection(factory);
			Set<byte[]> set = conn.keys((Constants.REDIS_SHIRO_SESSION + "*").getBytes());
			for (byte[] key : set) {
				list.add(SerializeUtil.deserialize(conn.get(key), SimpleSession.class));
			}
		} finally {
			RedisConnectionUtils.releaseConnection(conn, factory);
		}
		return list;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		saveSession(session);
		return sessionId;
	}

	private void saveSession(Session session) {
		if (session == null || session.getId() == null)
			throw new UnknownSessionException("Session is NULL");
		byte[] sessionKey = buildRedisSessionKey(session.getId());
		int sessionTimeOut = PropertiesUtil.getInt("session.maxInactiveInterval", EXPIRE_TIME);
		byte[] value = SerializeUtil.serialize(session);
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection conn = null;
		try {
			conn = RedisConnectionUtils.getConnection(factory);
			conn.set(sessionKey, value, Expiration.seconds(sessionTimeOut), SetOption.UPSERT);
		} finally {
			RedisConnectionUtils.releaseConnection(conn, factory);
		}
		
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		byte[] sessionKey = buildRedisSessionKey(sessionId);
		RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
		RedisConnection conn = null;
		try {
			conn = RedisConnectionUtils.getConnection(factory);
			byte[] value = conn.get(sessionKey);
			if (value == null) {
				return null;
			}
			Session session = SerializeUtil.deserialize(value, SimpleSession.class);
			return session;
		} finally {
			RedisConnectionUtils.releaseConnection(conn, factory);
		}
	}

}
