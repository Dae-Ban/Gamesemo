package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.GameInfoMapper;
import com.example.demo.model.GameInfo;

@Service
public class GameInfoService {

	@Autowired
	GameInfoMapper gameInfoMapper;

	public GameInfo getGameInfo(String gnum, String state) {
		return gameInfoMapper.getGameInfo(gnum, state);
	}

	public List<GameInfo> getGameInfosByGnum(int gnum) {
		return gameInfoMapper.getGameInfosByGnum(gnum);
	}

	public GameInfo getGameById(int gnum) {
		return gameInfoMapper.getGameById(gnum);
	}

	public List<GameInfo> getPlatforms(int gnum) {
		return gameInfoMapper.getPlatorms(gnum);
	}

	public List<GameInfo> getByGame(int gnum) {
		return gameInfoMapper.getByGame(gnum);
	}

	public List<GameInfo> getPlatformsByGame(int gnum) {
		return gameInfoMapper.getPlatformsByGame(gnum);
	}

	

}
