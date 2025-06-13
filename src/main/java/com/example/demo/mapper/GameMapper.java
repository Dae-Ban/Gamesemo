package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.GameInfo;
import com.example.demo.model.Pagenation;

@Mapper
public interface GameMapper {

	public List<GameInfo> getGameList(Pagenation pgn);
	public int getCount(Map<String, String> filter);


}
