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
	private GameMapper mapper;

	public List<GameInfo> getGameList(Pagenation pgn) {
		return mapper.getGameList(pgn);
	}

	public int getCount(String giState, String giPlatform) {
		Map<String, String> filter = new HashMap<>();
		filter.put("giState", giState);
		filter.put("giPlatform", giPlatform);
		return mapper.getCount(filter);
	}

	public List<GameInfo> search(String keyword) {
		return mapper.search(keyword);
	}
}
