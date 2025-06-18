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
	
    @GetMapping("/scrapmarge")
    public void scrapMarge() {
    	service.scrapMarge();
    }

	@GetMapping("/gameinfoupdate")
	public String updateGameInfo() {
		service.updateGameInfo();
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
	
	@GetMapping("/margesteamtop")
	public String margeSteamTop() {
		data.margeSteamTop();
		return "steam new marge";
	}
	
	@GetMapping("/margedirectnew")
	public String margeDirectNew() {
		data.margeDirectNew();
		return "direct new marge";
	}
	
	@GetMapping("/margenintendodc")
	public String margeNintendoDC() {
		data.margeNintendoDC();
		return "nintendo dc marge";
	}
	
	@GetMapping("/margenintendonew")
	public String margeNintendoNew() {
		data.margeNintendoNew();
		return "nintendo new marge";
	}
	
	@GetMapping("/margeplanetnew")
	public String margePlanetNew() {
		data.margePlanetNew();
		return "planet new marge";
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
	
	@GetMapping("/margesteamapi")
	public String margeSteamApi() {
		steamApi.margeSteamApi();
		return "스팀 api 갱신";
	}
}
