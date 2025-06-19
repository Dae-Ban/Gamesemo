package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.YouTubeVideoMapper;
import com.example.demo.model.YouTubeVideo;
import com.example.demo.util.YouTubeUtil;

@Service
public class YouTubeVideoService {
	@Autowired
	private YouTubeVideoMapper youTubeVideoMapper;

	@Autowired
	private YouTubeUtil youTubeUtil;

	public List<YouTubeVideo> search(String keyword) {
	    return search(keyword, 5, "relevance", "KR", "ko");
	}
	public List<YouTubeVideo> getReviewVideos(int gNum) {
		return youTubeVideoMapper.getReviewVideos(gNum);
	}

	public List<YouTubeVideo> search(String keyword, int maxResults, String order, String regionCode, String language) {
	    return youTubeUtil.search(keyword, maxResults, order, regionCode, language);
	}
	public List<YouTubeVideo> getOrFetchReviewVideos(int gnum, String keyword) {
	    List<YouTubeVideo> cached = youTubeVideoMapper.getOrFetchReviewVideos(gnum);

	    if (!cached.isEmpty()) {
	        return cached;
	    }

	    List<YouTubeVideo> apiResult = youTubeUtil.search(keyword, 5, "relevance", "KR", "ko");

	    for (YouTubeVideo video : apiResult) {
	        try {
	            youTubeVideoMapper.insertVideo(video, gnum);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return apiResult;
	}
	

}
