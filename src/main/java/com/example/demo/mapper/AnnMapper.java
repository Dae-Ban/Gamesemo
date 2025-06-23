package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Announcement;

@Mapper
public interface AnnMapper {
	int count();

	int countFiltered(@Param("search") String search, @Param("keyword") String keyword);

	Announcement getContent(int no);

	List<Announcement> getAnnList(Map<String, Object> params);
}
