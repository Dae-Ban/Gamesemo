package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.GameMapper;
import com.example.demo.model.GameInfo;
import com.example.demo.model.Pagenation;

@Service
public class GameService {
	@Autowired
	private GameMapper gameMapper;

	public List<GameInfo> getGameList(Pagenation pgn) {
		return gameMapper.getGameList(pgn);
	}
	


}