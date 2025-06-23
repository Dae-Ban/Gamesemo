package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.GameInfo;
import com.example.demo.model.YouTubeVideo;

@Mapper
public interface GameInfoMapper {


	List<GameInfo> selectTop10GamesByPlatform(String platform);

	GameInfo getGameInfo(@Param("gnum") String gnum, @Param("state") String state);

	List<GameInfo> getGameInfosByGnum(int gnum);

	GameInfo getGameById(int gnum);

	List<GameInfo> getPlatorms(int gnum);

	List<YouTubeVideo> getReviewVideos(int gnum);

	List<GameInfo> getByGame(int gnum);

	List<GameInfo> getPlatformsByGame(int gnum);
	
}
