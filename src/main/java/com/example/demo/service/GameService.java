package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.GameMapper;
import com.example.demo.model.GameInfo;
import com.example.demo.model.Pagenation;

@Service
public class GameService {
	@Autowired
	private GameMapper mapper;

	public List<GameInfo> getGameList(Pagenation pgn) {
		return mapper.getGameList(pgn);
	}

	public int getCount() {
		return mapper.getCount();
	}
}
