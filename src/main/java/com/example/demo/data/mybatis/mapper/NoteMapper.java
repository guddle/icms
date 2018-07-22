package com.example.demo.data.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.example.demo.jpa.domain.Note;

public interface NoteMapper {

	@Select("Select * from note ")
//	@Results({
//		@Result(id=true,property="id", column="id"),
//		@Result(property="name",column="name"),
//		@Result(property="tags",column="tags_id",javaType=List.class,
//		many=@Many(select="select *from "))
//		})
	List<Note> getAll();
}
