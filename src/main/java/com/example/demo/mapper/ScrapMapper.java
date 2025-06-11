package com.example.demo.mapper;

import java.util.List;

import com.example.demo.model.ScrapData;

public interface ScrapMapper {

	public void steamDCClean();
	public void steamDCInsert(ScrapData scrap);
	public List<ScrapData> getSteamDC();
	
	public void steamNewClean();
	public void steamNewInsert(ScrapData scrap);
	public List<ScrapData> getSteamNew();

}
