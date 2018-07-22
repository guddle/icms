package com.ictframe.data.repository;


import com.ictframe.data.entity.User;

public interface UserRepository extends BaseRepository<User, Integer> {

	User findByUsername(String username);
	
//	List<Role> getRoles();
}
