package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.Game;

public interface GameDataMapper {
	// 스팀 api
	public void insertSteamApi(List<Game> batch);
	public void margeSteamApi(Game steamApi);
	public List<Long> selectAllAppIds();
	public int getGNum(String nTitle);
	
	// from 스크랩
	public void margeSteamTop();
	public void margeSteamDC();
	public void margeSteamNew();
	public void margeDirectNew();
	public void margeNintendoDC();
	public void margeNintendoNew();
	public void margePlanetNew();
}
