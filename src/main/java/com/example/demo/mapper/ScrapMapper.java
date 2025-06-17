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
	
	public void steamTopClean();
	public void steamTopInsert(ScrapData scrap);
	public List<ScrapData> getSteamTop();
	
	public void directNewClean();
	public void directNewInsert(ScrapData scrap);
	public List<ScrapData> getDirectNew();
	
	public void nintendoDCClean();
	public void nintendoDCInsert(ScrapData scrap);
	public List<ScrapData> getNintendoDC();
	
	public void nintendoNewClean();
	public void nintendoNewInsert(ScrapData scrap);
	public List<ScrapData> getNintendoNew();
	
	public void planetNewClean();
	public void planetNewInsert(ScrapData scrap);
	public List<ScrapData> getPlanetNew();
	
}
