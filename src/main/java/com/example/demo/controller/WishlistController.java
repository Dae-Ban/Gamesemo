package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.GameInfo;
import com.example.demo.model.Member;
import com.example.demo.model.Wishlist;
import com.example.demo.service.GameInfoService;
import com.example.demo.service.MemberService;
import com.example.demo.service.WishlistService;

import ch.qos.logback.classic.spi.PlatformInfo;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("wishlist")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private GameInfoService gameInfoService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/add")
	@ResponseBody
	public String addToWishlist(@RequestParam("gnum") int gnum, HttpSession session) {
		Member user = (Member) session.getAttribute("loginUser");
		if (user == null)
			return "not_logged_in";

		Wishlist w = new Wishlist();
		w.setGNum(gnum);
		w.setId(user.getId());

		int result = wishlistService.addWishlist(w);
		if (result > 0)
			return "success";
		else
			return "already_exists";
	}

	@PostMapping("ajaxLogin")
	@ResponseBody
	public String ajaxLogin(@RequestParam("id") String id, @RequestParam("pw") String pw, HttpSession session) {
		// 정상 작동

		Member member = memberService.findById(id); // ID로만 조회
		if (member != null && passwordEncoder.matches(pw, member.getPw())) {
			session.setAttribute("loginUser", member);
			return "success";
		}
		return "fail";
	}
	
	

}
