package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.GameInfo;

@Mapper
public interface GameInfoMapper {

	public void gameInfoClean();
	public void insertGameInfo(GameInfo gameinfo);
	public List<GameInfo> gameInfoList();

}
