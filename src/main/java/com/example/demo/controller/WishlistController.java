package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.mapper.WishlistMapper;
import com.example.demo.model.GameInfo;
import com.example.demo.model.Member;
import com.example.demo.model.Wishlist;
import com.example.demo.service.MemberService;
import com.example.demo.service.WishlistService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/wishlist")
public class WishlistController {

	@Autowired
	private WishlistService wishlistService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private WishlistMapper wishlistMapper;

	
	@PostMapping("/gameAdd")
	@ResponseBody
	public String addToWishlist(@RequestParam("gnum") int gnum, HttpSession session) {
		Member user = (Member) session.getAttribute("loginMember");
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
		
		Member user = (Member) session.getAttribute("loginMember");
			
		Member member = memberService.findById(id); // ID로만 조회
		if (member != null && passwordEncoder.matches(pw, member.getPw())) {
			session.setAttribute("loginMember", member);
			return "success";
		}
		return "fail";
	}

	@GetMapping("")
	public String viewWishlist(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "order", defaultValue = "recent") String order,
			@RequestParam(name = "page", defaultValue = "1") int page, HttpSession session, Model model) {

		Member loginMember = (Member) session.getAttribute("loginMember");  
	    if (loginMember == null) {
	        session.setAttribute("redirectAfterLogin", "/wishlist");
	        return "redirect:/member/login";
	    }
	    String id = loginMember.getId();  
	    
		int pageSize = 5;
		int offset = (page - 1) * pageSize;

		int total = wishlistMapper.countWishlist(id, keyword);
		System.out.println("세션 loginMember.getId(): " + id);
		List<GameInfo> wishlist = (keyword != null && !keyword.trim().isEmpty())
				? wishlistMapper.searchWishlistPaged(id, "%" + keyword + "%", order, offset, pageSize)
				: wishlistMapper.getWishlistPaged(id, order, offset, pageSize);
		System.out.println("위시리스트 개수: " + wishlist.size());


		int totalPages = (int) Math.ceil((double) total / pageSize);

		System.out.println("아이디: " + id);
		System.out.println("총 위시리스트 수: " + total);
		System.out.println("검색어: " + keyword);
		System.out.println("order: " + order);
		System.out.println("가져온 위시리스트 크기: " + wishlist.size());

		for (GameInfo g : wishlist) {
			System.out.println(g.getGiTitle() + " / " + g.getGiThumb());
		}
		model.addAttribute("wishlist", wishlist);
		model.addAttribute("keyword", keyword);
		model.addAttribute("order", order);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);

		return "wishlist/wishList";
	}

	@PostMapping("/add")
	public String add(@RequestParam(name = "gNum") Long gNum, @RequestParam(name = "page", defaultValue = "1") int page,
			HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (id == null) {
			session.setAttribute("redirectAfterLogin", "/wishlist");
			return "redirect:/member/login";
		}
		if (id != null && !wishlistMapper.isGameInWishlist(id, gNum)) {
			wishlistMapper.addToWishlist(id, gNum);
		}
		return "redirect:/game?page=" + page;
	}

	@DeleteMapping("/delete")
	@ResponseBody
	public ResponseEntity<String> delete(@RequestParam(name = "gNum") Long gNum, HttpSession session) {
		String id = getLoginId(session);
		if (id != null) {
			wishlistMapper.removeFromWishlist(id, gNum);
			return ResponseEntity.ok("삭제 성공");
		}
		return ResponseEntity.badRequest().body("로그인 필요");
	}

	private String getLoginId(HttpSession session) {
		return (String) session.getAttribute("id");
	}
	
	@PostMapping("/isLogin")
	public String isLogin(HttpSession session) {
		
		if(session.getAttribute("loginMember") != null) {
			return "sucess";
		}
		
		return "fail";
		
	}
	

}
