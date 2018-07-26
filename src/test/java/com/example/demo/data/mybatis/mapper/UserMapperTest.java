/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo.data.mybatis.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.jpa.domain.Note;
import com.ictframe.data.entity.Permission;
import com.ictframe.data.entity.Role;
import com.ictframe.data.entity.User;
import com.ictframe.data.repository.UserRepository;

/**
 * Integration tests for {@link Note Mapper}.
 *
 * @author Jerry Yang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserMapperTest {
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserMapperTest.class);

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserRepository userRep;
	
	@Test
	public void testfindsAllUsers() {
		logger.debug("Find All User start");
		List<User> users = this.userMapper.selectUsers();
		assertThat(users).hasSize(2);
		for (User user : users) {
			logger.debug("Result:= {},{}",user.getId(),user.getUsername());
//			assertThat(note.getTags().size()).isGreaterThan(0);
			
		}
		logger.trace("Test log Trace end ----------------");
	}

	@Test
	public void testfindRolesByuserId() {
		String username = "jerry";
		logger.debug("先根据username={}查找User",username);
		User user = userRep.findByUsername(username);
		logger.debug("根据userid={}查找{}所拥有的角色,用户状态state：{},用户isexpired={},用户创建时间：{}",
				user.getId(),user.getUsername(), user.getState(), user.getIsexpired(), user.getCreatedate());
		List<Role> roles = user.getRoles();
		assertThat(roles).hasSize(1);
		for (Role role : roles) {
			logger.debug("Role = {}, desc = {}", role.getRole(), role.getDescription());
			List<Permission> permissions = role.getPermissions();
			assertThat(permissions).hasSize(1);
		}
	}
}
