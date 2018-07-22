package com.example.demo.data.mybatis.mapper;

import java.util.List;

import com.ictframe.data.entity.User;



public interface UserMapper {

	int insert(User record);

    List<User> selectUsers();
}
