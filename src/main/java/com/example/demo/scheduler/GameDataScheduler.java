package com.example.demo.scheduler;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.scraper.Scraper;
import com.example.demo.scraper.SteamApi;
import com.example.demo.service.GameDataService;
import com.example.demo.service.GameInfoService;

@Component
public class GameDataScheduler {
	private final Map<String, Scraper> scraperMap;
	@Autowired
	private GameInfoService info;
	@Autowired
	private GameDataService data;
	@Autowired
	private SteamApi steamApi;

	private GameDataScheduler(List<Scraper> scrapers) {
		// 이름으로 Scraper 매핑
		this.scraperMap = scrapers.stream().collect(Collectors.toMap(Scraper::getName, Function.identity()));
	}

	// /steamdc, /steamnew, /directnew, /nintendodc...
	private void scrap() {
		scraperMap.forEach((name, scraper) -> {
			System.out.println(name + " 스크랩 시작");
			scraper.scrap();
		});
	}
	
	private void margeIntoGame() {
		steamApi.margeSteamApi();
		data.margeSteamTop();
		data.margeSteamDC();
		data.margeSteamNew();
		data.margeNintendoExp();
		data.margeNintendoDC();
		data.margeNintendoNew();
		data.margeDirectNew();
		data.margePlanetNew();
	}
	
	@Scheduled(cron = "0 0 4 * * *")
	public void run() {
		scrap();
		margeIntoGame();
		info.scrapMarge();
		info.updateGameInfo();
		System.out.println("DB 갱신 완료");
	}
}
