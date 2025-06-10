package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SteamDCScraper;

@RestController
@RequestMapping("/scrap")
public class ScrapController {
	@Autowired
	private SteamDCScraper steamDC;
	
	@GetMapping("/steamdc")
	public String steamDCScrap() {
		steamDC.scrap();
		return "스팀 할인 스크랩";
	}
}
