package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.GameInfo;
import com.example.demo.model.Member;
import com.example.demo.service.GameInfoService;
import com.example.demo.util.YouTubeUtil;
import com.example.demo.util.YouTubeVideo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/game")
public class GameController {

	@Autowired
    private GameInfoService gameInfoService;
	
	@Autowired
	private YouTubeUtil youTubeUtil;
	//
	@GetMapping("/gameContent")
	public String gameContent(@RequestParam("gnum") String gnum,HttpSession session, Model model) {
		  // 1) 같은 G_NUM 으로 모든 플랫폼 정보 조회
	    List<GameInfo> platformList = gameInfoService.getGameInfosByGnum(gnum);
	    if (platformList.isEmpty()) {
	        return "error/404";
	    }
	    // 2) 대표 게임 정보 (첫 번째) 
	    GameInfo game = platformList.get(0);

	    // 3) 유저 세션
	    Member loginUser = (Member) session.getAttribute("loginUser");

	    // 4) 유튜브 리뷰 영상 
	    
	    //
	    List<YouTubeVideo> reviewVideos;
	    
	    if (true) { // 테스트용 조건문: 항상 더미 데이터 사용
	        reviewVideos = List.of(
	            new YouTubeVideo("test1", "1", "relevance"),
	            new YouTubeVideo("test2", "2", "relevance"),
	            new YouTubeVideo("test3", "3", "relevance"),
	            new YouTubeVideo("test4", "4", "relevance"),
	            new YouTubeVideo("test5", "5", "relevance")
	        );
	    }else {
	    	reviewVideos  = youTubeUtil.search(game.getGiTitle()  + " 리뷰", 0,"relevance","KR","ko");
	    }
	    // 5) 모델에 담기
	    model.addAttribute("platformList", platformList);
	    model.addAttribute("game", game);
	    model.addAttribute("loginUser", loginUser);
	    model.addAttribute("reviewVideos", reviewVideos);

	    return "game/gameContent";
	}
	
	
	
	
}