package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.GameInfo;
import com.example.demo.scraper.SteamApi;
import com.example.demo.service.GameDataService;
import com.example.demo.service.GameInfoService;

@RestController
@RequestMapping("/gamedata")
public class GameDataController {

	@Autowired
	private GameInfoService service;
	@Autowired
	private GameDataService data;
	@Autowired
	private SteamApi steamApi;
	
	@GetMapping("/gameinfos")
	public List<GameInfo> gameInfoList(){
		return service.gameInfoList();
	}

	@GetMapping("/gameinfoupdate")
	public String updateGameInfo() {
		service.insertGameInfo();
		return "game_info 업데이트";
	}

	@GetMapping("/margesteamdc")
	public String margeSteamDC() {
		data.margeSteamDC();
		return "steam dc marge";
	}
	
	@GetMapping("/margesteamnew")
	public String margeSteamNew() {
		data.margeSteamNew();
		return "steam new marge";
	}
	
	// 주의! 스팀 api 20만여개의 데이터를 DB에 저장하는 메소드
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
