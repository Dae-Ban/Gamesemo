package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.YouTubeVideo;

@Mapper
public interface YouTubeVideoMapper {

	public List<YouTubeVideo> getOrFetchReviewVideos(int gnum);
	public List<YouTubeVideo> getReviewVideos(int gnum);

	 void insertVideo(@Param("video") YouTubeVideo video, @Param("gnum") int gnum);
	
	
	
}
