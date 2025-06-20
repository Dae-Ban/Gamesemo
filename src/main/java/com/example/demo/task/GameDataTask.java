package com.example.demo.task;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.merger.GameDataMerger;
import com.example.demo.scraper.Scraper;
import com.example.demo.scraper.SteamApi;
import com.example.demo.service.GameInfoService;

@Component
public class GameDataTask {
	private final Map<String, Scraper> scraperMap;
	private final Map<String, GameDataMerger> mergerMap;
	@Autowired
	private GameInfoService info;
	@Autowired
	private SteamApi steamApi;

	private GameDataTask(List<Scraper> scrapers, List<GameDataMerger> mergers) {
		this.scraperMap = scrapers.stream().collect(Collectors.toMap(Scraper::getName, Function.identity()));
		this.mergerMap = mergers.stream().collect(Collectors.toMap(GameDataMerger::getName, Function.identity()));
	}

	// /steamdc, /steamnew, /directnew, /nintendodc...
	private void scrap() {
		scraperMap.forEach((name, scraper) -> {
			System.out.println(name + " 스크랩 시작");
			scraper.scrap();
		});
	}
	
	private void mergeIntoGame() {
		steamApi.margeSteamApi();
		mergerMap.forEach((name, merger) -> {
			System.out.println(name + " game에 병합 시작");
			merger.merge();
		});
	}
	
	public void run() {
		System.out.println("🔥 DB갱신 시작");
		scrap();
		mergeIntoGame();
		info.scrapMerge();
		
		info.updateGameInfo();
		System.out.println("DB 갱신 완료");
	}
}
