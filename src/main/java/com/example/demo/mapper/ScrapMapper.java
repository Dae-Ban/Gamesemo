package com.example.demo.mapper;

import com.example.demo.model.ScrapData;

public interface ScrapMapper {

	void steamDCClean();
	void steamDCInsert(ScrapData scrap);

}
