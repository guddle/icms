package com.example.demo.jpa.service;

import java.util.Map;

import com.ictframe.data.entity.Role;
import com.ictframe.data.entity.User;


public interface ILoginService {

	User findByNmae(String name);

//	List<Role> findRolePermissions(User userId);

	User addUser(Map<String, Object> map);

	Role addRole(Map<String, Object> map);
}
