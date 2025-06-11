package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.Game;

public interface GameDataMapper {

	public void insertSteamApi(List<Game> batch);
	public List<Long> selectAllAppIds();
}
