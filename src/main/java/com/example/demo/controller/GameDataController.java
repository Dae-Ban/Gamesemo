package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.merger.GameDataMerger;
import com.example.demo.scraper.SteamApi;
import com.example.demo.service.GameInfoService;
import com.example.demo.task.GameDataTask;

@RestController
@RequestMapping("/gamedata")
public class GameDataController {

	@Autowired
	private GameDataTask task;
	@Autowired
	private GameInfoService service;
	@Autowired
	private SteamApi steamApi;

	private final Map<String, GameDataMerger> mergerMap;

	public GameDataController(List<GameDataMerger> mergers) {
		this.mergerMap = mergers.stream().collect(Collectors.toMap(GameDataMerger::getName, Function.identity()));
	}
	
	@GetMapping("/master")
	public ModelAndView masterButton() {
		return new ModelAndView("game/gamedataMaster");
	}
	
	@GetMapping("/master/update")
	public boolean updateDB() {
		try {
			task.run();
		} catch(Exception e) {
			return false;
		}
		return true;
	}

	@GetMapping("/marge" + "{target}")
	public ResponseEntity<String> margeIntoGame(@PathVariable("target") String target) {
		GameDataMerger merger = mergerMap.get(target);
		if (merger == null) {
			return ResponseEntity.badRequest().body("잘못된 merger 이름: " + target);
		}
		try {
			merger.merge();
			return ResponseEntity.ok(target + " merge 완료");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("❌ 병합 실패: " + e.getMessage());
		}
	}

	// 스크랩한 모든 데이터를 marge
	@GetMapping("/scrapmarge")
	public void scrapMarge() {
		service.scrapMarge();
	}

	// 스크랩한 정보를 marge한 쪽으로부터 game_info 업데이트
	@GetMapping("/gameinfoupdate")
	public String updateGameInfo() {
		service.updateGameInfo();
		return "game_info 업데이트";
	}

	// 스팀 api 갱신
	@GetMapping("/margesteamapi")
	public String margeSteamApi() {
		steamApi.margeSteamApi();
		return "스팀 api 갱신";
	}

	// 주의! 스팀 api 20만여개의 데이터를 DB에 저장하는 메소드
	// DB 초기화 후에만 사용할 것
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
