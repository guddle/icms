package com.example.demo.data.jpa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.example.demo.jpa.domain.Note;

@Component("noteService")
public class NoteServiceImpl implements NoteService{

	@Autowired
	private NoteRepository noteRepository;

	@Override
	public List<Note> findAll() {
		// TODO Auto-generated method stub
		return this.noteRepository.findAll();
	}

	@Override
	@Cacheable(value="note",key="#id")
	public Note findById(Long id) {
		// TODO Auto-generated method stub
		return this.noteRepository.findById(id);
	}
	
	@Override
	@Transactional(rollbackOn=RuntimeException.class)
//	@CachePut(key = "\"note_\" + #note.id")
	public void save(Note note) {
		this.noteRepository.save(note);
	}
	
	
}
