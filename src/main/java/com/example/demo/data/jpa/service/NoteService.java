package com.example.demo.data.jpa.service;

import java.util.List;

import com.example.demo.jpa.domain.Note;

public interface NoteService {

	List<Note> findAll();
	Note findById(Long id);
	void save(Note note);
}
