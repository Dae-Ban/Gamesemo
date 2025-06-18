package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Member;
import com.example.demo.service.MemberService;
import com.example.demo.service.WishlistService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("wishlist")
public class WishlistController {
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("addToWishlist")
	public String addToWishlist(@RequestParam("gNum")String gNum, HttpSession session, Model model ) {
		
		Member loginUser = (Member) session.getAttribute("loginUser");
		int result;
		
		if(loginUser == null) {
			//추후에 팝업 로그인 창을 넣고 로그인시 현재 게임컨텐츠 화면에 gNum값과 session값을 넘겨주는게 좋을꺼 같음.
			
		}
		String id = session.getId();
		result = wishlistService.addWishlist(gNum, id);
		
		
		return "";
	}
	
	@PostMapping("ajaxLogin")
	@ResponseBody
	public String ajaxLogin(@RequestParam String id,
	                        @RequestParam String pw,
	                        HttpSession session) {
	    Member member = memberService.modalLogin(id, pw);
	    if (member != null) {
	        session.setAttribute("loginUser", member);
	        return "success";
	    } else {
	        return "fail";
	    }
	}
	
	
}
