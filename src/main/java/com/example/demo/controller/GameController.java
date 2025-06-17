package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.GameInfo;
import com.example.demo.model.Pagenation;
import com.example.demo.service.GameService;

@Controller
@RequestMapping("/game")
public class GameController {
	@Autowired
	private GameService service;
	
	@GetMapping("")
	public String main(@RequestParam(name = "state", defaultValue = "dc") String giState,
			@RequestParam(name = "platform", defaultValue = "all") String giPlatform, Model model) {
		model.addAttribute("state", giState);
		model.addAttribute("platform", giPlatform);
		return "/game/games";
	}
	
	@ResponseBody
	@GetMapping("/list/{amount}")
	public List<GameInfo> list(@PathVariable("amount") int amount,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "state", defaultValue = "dc") String giState,
			@RequestParam(name = "platform", defaultValue = "all") String giPlatform) {
		System.out.println("📌 요청 상태: " + giState + ", 플랫폼: " + giPlatform);
		
		Pagenation pgn = new Pagenation(service.getCount(giState, giPlatform), amount, page);
		pgn.setGiState(giState);
		pgn.setGiPlatform(giPlatform);
		return service.getGameList(pgn);
	}
	
	@GetMapping("/{gNum}")
	public String details(@PathVariable("gNum") int gNum, @RequestParam(name = "page", defaultValue = "1") String page) {
		return "/game/details";
	}
}