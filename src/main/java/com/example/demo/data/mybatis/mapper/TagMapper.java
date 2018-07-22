package com.example.demo.data.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.example.demo.jpa.domain.Tag;

public interface TagMapper {

	@Select("Select *from tag ")
	List<Tag> getAll();
}
