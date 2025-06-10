package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.GameInfo;

@Mapper
public interface GameInfoMapper {
	List<GameInfo> selectTop10GamesByPlatform(String platform);
}
