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
package com.example.demo.data.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.data.mybatis.mapper.TagMapper;
import com.example.demo.jpa.domain.Note;
import com.example.demo.jpa.domain.Tag;

/**
 * Integration tests for {@link Note Mapper}.
 *
 * @author Jerry Yang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class NoteServiceTest {
	
	private static final Logger logger = LoggerFactory
			.getLogger(NoteServiceTest.class);

	@Autowired
	NoteService noteService;
	@Autowired
	TagMapper mapper;

	@Test
	public void findsAllNotes() {
		logger.debug("findsAllNotes start");
		List<Note> notes = this.noteService.findAll();
		assertThat(notes).hasSize(4);
		for (Note note : notes) {
			logger.debug("Result:= {},{}",note.getId(), note.getBody());
//			assertThat(note.getTags().size()).isGreaterThan(0);
		}
		logger.trace("Test log Trace end ----------------");
	}

	@Test
	public void findNoteById() {
		logger.debug("Frist Find Note By Id...........................");
		Note note = this.noteService.findById(1L);
//		assertThat(note).hasSize(2);
		assertNotNull(note);
		logger.debug("Result:= {},{},{}",note.getId(), note.getBody(),note.getTitle());
		logger.debug("Second Find Note By Id...........................");
		Note note1 = this.noteService.findById(1L);
		logger.debug("Result:= {},{},{}",note1.getId(), note1.getBody(),note1.getTitle());
	}
	
//	@Test
	public void saveNoteTest() {
		logger.debug("Save Note...........................");
		Note note = new Note();
		note.setId(5);
		note.setBody("Spring Boot Redis Cache Test");
		note.setTitle("Redis Cache!");
		List<Tag> tags = mapper.getAll();
		note.setTags(tags);
		noteService.save(note);
////		assertThat(note).hasSize(2);
//		assertNotNull(note);
//		logger.debug("Result:= {},{}",note.getId(), note.getBody());
//		logger.debug("Second Find Note By Id...........................");
//		Note note1 = this.noteService.findById(1L);
//		logger.debug("Result:= {},{}",note1.getId(), note.getBody());
//			assertThat(note.getTags().size()).isGreaterThan(0);
	}
}
