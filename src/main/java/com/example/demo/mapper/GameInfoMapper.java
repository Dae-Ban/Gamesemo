package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.GameInfo;

@Mapper
public interface GameInfoMapper {

	public void insertGameInfo(GameInfo gameinfo);

}
