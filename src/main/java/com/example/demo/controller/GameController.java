package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.mapper.GameInfoMapper;
import com.example.demo.model.GameInfo;

@Controller
public class GameController {

	@Autowired
	private GameInfoMapper gameInfoMapper;

//	@RequestMapping("/game/detail")
//	public String gameDetail(@RequestParam("giNum") int giNum, Model model) {
////		GameInfo game = gameInfoMapper.selectByGiNum(giNum);
//
//		if (game == null) {
//			model.addAttribute("error", "해당 게임 정보를 찾을 수 없습니다.");
//			return "error/errorPage"; // 예외 처리 페이지
//		}
//
//		model.addAttribute("game", game);
//		return "game/detail"; // /WEB-INF/views/game/detail.jsp
//	}

//	@RequestMapping("/gameContent")
//	public String game_content(/*@RequestParam(value="appid")String appid,*/ Model model){
//		
//		String appid ="1332010";
//		try {
//			/*
//			 * String url = "https://store.steampowered.com/api/appdetails?appids=" + appid
//			 * + "&cc=kr&l=koreana"; RestTemplate restTemplate = new RestTemplate(); String
//			 * json = restTemplate.getForObject(url, String.class);
//			 * 
//			 * ObjectMapper mapper = new ObjectMapper(); JsonNode root =
//			 * mapper.readTree(json); JsonNode shortDescNode =
//			 * root.path(String.valueOf(appid)).path("data").path("short_description");
//			 * 
//			 * String shortDesc = shortDescNode.asText(); // 예: "이 게임은 블라블라입니다."
//			 * model.addAttribute("shortDesc", shortDesc); model.addAttribute("appid",
//			 * appid);
//			 */
//			
//			 @Autowired
//			    private GameInfoMapper gameInfoMapper;
//
//			    @RequestMapping("/game/detail")
//			    public String gameDetail(@RequestParam("giNum") int giNum, Model model) {
//			        GameInfo game = gameInfoMapper.selectByGiNum(giNum);
//
//			        if (game == null) {
//			            model.addAttribute("error", "해당 게임 정보를 찾을 수 없습니다.");
//			            return "error/errorPage"; // 예외 처리 페이지
//			        }
//
//			        model.addAttribute("game", game);
//			        return "game/detail"; // /WEB-INF/views/game/detail.jsp
//			    }
//
//        } catch (Exception e) {
//            model.addAttribute("shortDesc", "설명을 불러올 수 없습니다.");
//        }		
//		return "gameContent";
//	}
}
