package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.SteamApi;

@RestController
@RequestMapping("/gamedata")
public class GameDataController {
	@Autowired
	private SteamApi steamApi;

	@GetMapping("getsteamapi")
	public String getSteamApi() {
		steamApi.insertSteamApi();
		return "스팀 api 삽입";
	}
}
