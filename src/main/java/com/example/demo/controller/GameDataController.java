package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.GameDataService;
import com.example.demo.service.GameInfoService;
import com.example.demo.service.SteamApi;

@RestController
@RequestMapping("/gamedata")
public class GameDataController {

	@Autowired
	private GameInfoService service;
	@Autowired
	private GameDataService data;
	@Autowired
	private SteamApi steamApi;

	@GetMapping("/gameinfo")
	public String updateGameInfo() {
		service.insertGameInfo();
		return "game_info 업데이트";
	}

	@GetMapping("/margesteamdc")
	public String margeSteamDC() {
		data.margeSteamDC();
		return "steam dc marge";
	}
	
	@GetMapping("/getsteamapi")
	public String getSteamApi() {
		try {
			steamApi.insertSteamApi();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("🔥 getSteamApi() 실행됨");
		}
		return "스팀 api 삽입";
	}
}
