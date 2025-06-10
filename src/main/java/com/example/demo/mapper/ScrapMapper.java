package com.example.demo.mapper;

import com.example.demo.model.GameInfo;

public interface ScrapMapper {

	void steamClean();
	void steamInsert(GameInfo g);

}
