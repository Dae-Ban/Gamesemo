package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.GameInfo;
import com.example.demo.model.Member;
import com.example.demo.model.YouTubeVideo;
import com.example.demo.service.GameInfoService;
import com.example.demo.service.WishlistService;
import com.example.demo.service.YouTubeVideoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/game")
public class GameController {

	@Autowired
    private GameInfoService gameInfoService;
	
	@Autowired
	private YouTubeVideoService youtubeVideoService;
	
	@Autowired
	private WishlistService wishlistService;
	
	@GetMapping("/gameContent")
	public String gameContent(@RequestParam("gnum") int gnum,HttpSession session, Model model) {
		  // 1) 같은 G_NUM 으로 모든 플랫폼 정보 조회
	    List<GameInfo> platformList = gameInfoService.getGameInfosByGnum(gnum);
	    
	    
	    if (platformList.isEmpty()) {
	        return "error/404";
	    }
	    // 2) 대표 게임 정보 (첫 번째) 
	    GameInfo game = platformList.get(0);

	    // 3) 유저 세션
	    Member loginUser = (Member) session.getAttribute("loginUser");
	    boolean wishlisted = false;
	    if (loginUser != null) {
	        wishlisted = wishlistService.wishlistExists(gnum, loginUser.getId());
	    }

	    // 4) 유튜브 리뷰 영상 
	    List<YouTubeVideo> reviewVideos;
	    
	    //테스트 용도
//	    if (false) { 
//	        reviewVideos = List.of(
//	            new YouTubeVideo("test1", "1", "relevance"),
//	            new YouTubeVideo("test2", "2", "relevance"),
//	            new YouTubeVideo("test3", "3", "relevance"),
//	            new YouTubeVideo("test4", "4", "relevance"),
//	            new YouTubeVideo("test5", "5", "relevance")
//	        );
//	    }else {
//	    	reviewVideos = youtubeVideoService.getOrFetchReviewVideos(gnum, game.getGiTitle() + " 리뷰");
//	    }
	    
	    reviewVideos = youtubeVideoService.getOrFetchReviewVideos(gnum, game.getGiTitle() + " 리뷰");
	    
	    // 5) 모델에 담기
	    model.addAttribute("wishlisted", wishlisted);
	    model.addAttribute("platformList", platformList);
	    model.addAttribute("game", game);
	    model.addAttribute("loginUser", loginUser);
	    model.addAttribute("reviewVideos", reviewVideos);

	    
	    return "game/gameContent";
	}
	
//	@GetMapping("/game/detail/{gnum}")
//	public String showGameDetail(@PathVariable int gnum,
//	                             Model model,
//	                             HttpSession session,
//	                             HttpServletRequest request) {
//
//	    GameInfo game = gameInfoService.getGameById(gnum);
//	    List<GameInfo> platformList = gameInfoService.getPlatforms(gnum);
////	    List<YouTubeVideo> reviewVideos = youtubeVideoService.getOrFetchReviewVideos(gnum, game.getGiTitle() + " 리뷰");
//	    List<YouTubeVideo> reviewVideos = youtubeVideoService.getReviewVideos(gnum);
//
//	    model.addAttribute("game", game);
//	    model.addAttribute("platformList", platformList);
//	    model.addAttribute("reviewVideos", reviewVideos);
//
//	    // 현재 URI를 세션에 저장 (로그인 후 redirect 용도)
//	    String uri = request.getRequestURI();
//	    String query = request.getQueryString();
//	    session.setAttribute("redirectAfterLogin", uri + (query != null ? "?" + query : ""));
//
//	    return "game/detail"; // JSP 파일 경로
//	}
	
	
	
	
}