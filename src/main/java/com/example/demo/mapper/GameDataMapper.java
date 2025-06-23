package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.Game;
import com.example.demo.model.GameInfo;

public interface GameDataMapper {
	// 스팀 api
	public void insertSteamApi(List<Game> batch);
	public void mergeSteamApi(Game steamApi);
	public List<Long> selectAllAppIds();
	public int getGNum(String nTitle);
	
	// from 스크랩
	public void mergeSteamTop();
	public void mergeNintendoExp();
	public void mergeSteamDC();
	public void mergeSteamNew();
	public void mergeDirectNew();
	public void mergeNintendoDC();
	public void mergeNintendoNew();
	public void mergePlanetNew();
	
	public void scrapMergeClean();
	public void scrapMerge(GameInfo gameinfo);

	public void updateGameInfo();
}
