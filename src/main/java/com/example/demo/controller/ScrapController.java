package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.Scraper;

@RestController
@RequestMapping("/scrap")
public class ScrapController {
	private final Map<String, Scraper> scraperMap;
	
    public ScrapController(List<Scraper> scrapers) {
        // 이름으로 Scraper 매핑
        this.scraperMap = scrapers.stream()
            .collect(Collectors.toMap(Scraper::getName, Function.identity()));
    }
	
    // /steamdc, /steamnew
    @GetMapping("/{target}")
    public ResponseEntity<String> scrap(@PathVariable("target") String target) {
        Scraper scraper = scraperMap.get(target);
        if (scraper == null) {
            return ResponseEntity.badRequest().body("잘못된 스크래퍼 이름: " + target);
        }
        scraper.scrap();
        return ResponseEntity.ok(target + " 스크랩 완료");
    }
}
