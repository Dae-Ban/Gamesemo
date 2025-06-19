package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Member;
import com.example.demo.model.Report;
import com.example.demo.service.ReportService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/rcreport")
public class ReportController {

	@Autowired
    private ReportService reportService;
	
	private Member ensureLoginSession(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            loginMember = new Member();
            loginMember.setId("minjung1");    // 테스트용 기본값
            session.setAttribute("loginMember", loginMember);
        }
        return loginMember;
    }	
	
	
	@GetMapping("/report/form")
    public String reportForm(@RequestParam("rp_table") String rp_table,
                             @RequestParam("board_num") int board_num,
                             Model model, HttpSession session) {
        ensureLoginSession(session);
        model.addAttribute("rp_table", rp_table);
        model.addAttribute("board_num", board_num);
        return "report/reportForm";
    }

    @PostMapping("/report/insert")
    public String insertReport(@ModelAttribute Report report, HttpSession session, RedirectAttributes ra) {
        Member loginMember = ensureLoginSession(session);
//      report.setId(loginMember.getId());
        report.setId("minjung1");
        report.setRp_date(new java.sql.Timestamp(System.currentTimeMillis()));
        report.setRp_status("PENDING");

        if (reportService.existsByUserAndTarget(report)) {
            ra.addFlashAttribute("msg", "이미 신고한 게시글입니다.");
            
            if(report.getRp_table().equals("REVIEW_BOARD")) {
            	return "redirect:/review/view?rb_num=" + report.getBoard_num();        	
            }else {
            	return "redirect:/community/view?cb_num=" + report.getBoard_num();        	
            }
            
        }

        reportService.insertReport(report);
        
        if(report.getRp_table().equals("REVIEW_BOARD")) {
        	return "redirect:/review/view?rb_num=" + report.getBoard_num();        	
        }else {
        	return "redirect:/community/view?cb_num=" + report.getBoard_num();        	
        }
           	
       
    }
	
	
	
}
