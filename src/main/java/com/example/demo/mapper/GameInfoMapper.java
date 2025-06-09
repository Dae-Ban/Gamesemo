package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.GameInfo;

public class GameInfoMapper {
	List<GameInfo> selectTop10GamesByPlatform(String platform);
}
