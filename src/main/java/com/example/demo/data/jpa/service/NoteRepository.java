package com.example.demo.data.jpa.service;

import java.util.List;

import org.springframework.data.repository.Repository;

import com.example.demo.jpa.domain.Note;

public interface NoteRepository extends Repository<Note, Long> {

//	Page<Note> findAll(Pageable pageable);
	Note findById(long id);
	List<Note> findAll();
	void save(Note note);
}
