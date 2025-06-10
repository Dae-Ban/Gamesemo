package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.AdminService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	 	@Autowired
	    private AdminService adminService;

	    @GetMapping("/adminloginform")
	    public String loginPage() {
	        return "adminloginform";
	    }

	    @PostMapping("/login")
	    public String login(@RequestParam("admin_id") String admin_id,
	                        @RequestParam("admin_pw") String admin_pw,
	                        HttpSession session) {
	        if (adminService.login(admin_id, admin_pw)) {
	        	System.out.println("✅ 로그인 성공: " + admin_id);
	            session.setAttribute("admin", admin_id);
	            return "redirect:/admin/home";
	        }
	        System.out.println("❌ 로그인 실패: " + admin_id);
	        return "adminloginform"; // 실패 시 로그인 페이지로 다시 이동
	    }
	    
}
