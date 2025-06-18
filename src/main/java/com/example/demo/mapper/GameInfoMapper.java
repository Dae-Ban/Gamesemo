package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.GameInfo;

@Mapper
public interface GameInfoMapper {
	
	public void scrapMargeClean();
	public void scrapMarge(GameInfo gameinfo);

	public void gameInfoClean();
	public void updateGameInfo();
	public List<GameInfo> gameInfoList();

}
