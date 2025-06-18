package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.GameInfo;

@Mapper
public interface GameInfoMapper {
	List<GameInfo> selectTop10GamesByPlatform(String platform);

	GameInfo getGameInfo(@Param("gnum")String gnum,@Param("state")String state);

	List<GameInfo> getGameInfosByGnum(String gnum);
}
