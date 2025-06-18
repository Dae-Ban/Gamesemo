package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.GameInfo;
import com.example.demo.model.Pagenation;

@Mapper
public interface GameMapper {
	// 게임 목록
	public List<GameInfo> getGameList(Pagenation pgn);
	// 전체 개수
	public int getCount(Map<String, String> filter);
	// 검색
	public List<GameInfo> search(String keyword);

}