package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Announcement;

@Mapper
public interface AnnMapper {
    public int count();
    public List<Announcement> getAnnList(@Param("page") int page, @Param("search") String search, @Param("keyword") String keyword);
	public Announcement getContent(int no);
}
