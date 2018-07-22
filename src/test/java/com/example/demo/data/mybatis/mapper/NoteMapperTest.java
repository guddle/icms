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

/**
 * Integration tests for {@link Note Mapper}.
 *
 * @author Jerry Yang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@MapperScan("**.mybatis.mapper.**")
public class NoteMapperTest {
	
	private static final Logger logger = LoggerFactory
			.getLogger(NoteMapperTest.class);

	@Autowired
	NoteMapper noteMapper;

	@Test
	public void findsAllNotes() {
		logger.debug("findsAllNotes start");
		List<Note> notes = this.noteMapper.getAll();
		assertThat(notes).hasSize(4);
		for (Note note : notes) {
			logger.debug("Result:= {},{}",note.getId(), note.getBody());
//			assertThat(note.getTags().size()).isGreaterThan(0);
		}
		logger.trace("Test log Trace end ----------------");
	}

}
