package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Pagenation;
import com.example.demo.service.GameService;

@Controller
@RequestMapping("/games")
public class GameController {
	@Autowired
	private GameService service;
	
	@GetMapping("")
	public String main() {
		return "/game/games";
	}
	
	@GetMapping("/list")
	public String list(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		Pagenation pgn = new Pagenation(service.getCount(), 10, page);
		model.addAttribute("pgn", pgn);
		model.addAttribute("list", service.getGameList(pgn));
		return "/game/gameList";
		
	}
}
