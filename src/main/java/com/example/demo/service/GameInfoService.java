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

	public List<GameInfo> getGameInfosByGnum(String gnum) {
		return gameInfoMapper.getGameInfosByGnum(gnum);
	}

}
