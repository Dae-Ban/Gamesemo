package com.example.demo.merger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.GameDataMapper;

@Service
public class SteamTopMerger implements GameDataMerger {
	@Autowired
	private GameDataMapper mapper;

	@Override
	public void merge() {
		mapper.margeSteamTop();
	}

	@Override
	public String getName() {
		return "steamtop";
	}
}
