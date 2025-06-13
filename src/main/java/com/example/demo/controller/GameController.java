package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Pagenation;
import com.example.demo.service.GameService;

@Controller
@RequestMapping("/game")
public class GameController {
	@Autowired
	private GameService service;
	
	@GetMapping("")
	public String main(Model model) {
		model.addAttribute("headline", "할인 중인");
		return "/game/games";
	}
	
	@GetMapping("/list")
	public String list(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		Pagenation pgn = new Pagenation(service.getCount(), 20, page);
		System.out.println(pgn.getStartRow());
		model.addAttribute("pgn", pgn);
		model.addAttribute("list", service.getGameList(pgn));
		return "/game/gameList";
	}
	
	@GetMapping("/{gNum}")
	public String details(@PathVariable("gNum") int gNum, @RequestParam(name = "page", defaultValue = "1") String page) {
		return "/game/details";
	}
}
