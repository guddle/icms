package com.example.demo.jpa.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ictframe.data.entity.Role;
import com.ictframe.data.entity.User;
import com.ictframe.data.repository.UserRepository;


@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	
	private final static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	
	@Autowired
	private UserRepository userRepository;
//	@Autowired
//	private RoleRepository roleRepository;
	
	@Override
	public User addUser(Map<String, Object> map) {
		if(map == null)
			return null;
		User user = new User();
		user.setUsername(map.get("userName").toString());
		user.setPassword(map.get("password").toString());
		userRepository.save(user);
		return user;
	}
	
	@Override
	public Role addRole(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@Cacheable(value = "user", key = "#name")
	public User findByNmae(String name) {
		logger.debug("{} Find User by name = {}",this.getClass().getName(), name);
		return userRepository.findByUsername(name);
	}
	
//	@Override
//	public List<Role> findRolePermissions() {
//		return userRepository.getRoles()
//	}
}
