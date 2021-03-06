/*
 * Copyright 2012-2016 the original author or authors.
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

package com.example.demo.jpa.web;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.data.jpa.service.NoteServiceImpl;
import com.example.demo.jpa.domain.Note;
import com.example.demo.jpa.repository.NoteRepository;
import com.ictframe.core.util.Encrypt;

@Controller
public class IndexController {
	private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private NoteServiceImpl noteService;

	@RequestMapping("/index")
	@Transactional(readOnly = true)
	public ModelAndView index() {
//		Session session = SecurityUtils.getSubject().getSession();
//		session.
		List<Note> notes = this.noteRepository.findAll();
		ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("notes", notes);
		return modelAndView;
	}

	@GetMapping("/getnotes")
	@Transactional(readOnly = true)
	public @ResponseBody List<Note> getNotes(){
		List<Note> notes = this.noteService.findAll();
		return notes;
	}
	@RequestMapping("/login")
	public String login(String username, String password) {
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, Encrypt.md5(password));
//			token.setRememberMe(true);
			SecurityUtils.getSubject().login(token);
			return "index";
		}catch (Exception e) {
			return "login";
		}
	}
	@GetMapping("/403")
	public String _403(){
		return "403";
	}
}
