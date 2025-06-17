package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.GameDataMapper;

@Service
public class GameDataService {
	@Autowired
	private GameDataMapper mapper;
	
	public void margeSteamDC() {
		mapper.margeSteamDC();
	}
	public void margeSteamNew() {
		mapper.margeSteamNew();
	}
	public void margeDirectNew() {
		mapper.margeDirectNew();
	}
	public void margeNintendoDC() {
		mapper.margeNintendoDC();
	}
	public void margeNintendoNew() {
		mapper.margeNintendoNew();
	}
	public void margePlanetNew() {
		mapper.margePlanetNew();
	}
	public void margeSteamTop() {
		mapper.margeSteamTop();
	}
}
