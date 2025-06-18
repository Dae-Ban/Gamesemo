package com.example.demo.controller;

import com.example.demo.model.ReportDTO;
import com.example.demo.model.Review;
import com.example.demo.page.AdminPagenation;
import com.example.demo.model.Community;
import com.example.demo.service.AdminReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminReportController {

    @Autowired
    private AdminReportService adminReportService;

    @GetMapping("/adminReport")
    public String reportList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        int limit = 10;
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;

        List<ReportDTO> list = adminReportService.getPagedReportList(startRow, endRow);
        int total = adminReportService.getTotalReportCount();

        AdminPagenation p = new AdminPagenation(total, page, limit, 5);

        model.addAttribute("reportList", list);
        model.addAttribute("p", p); 
        model.addAttribute("page", page);

        return "/admin/adminReport";
    }

    @PostMapping("/processReport")
    public String processReport(@RequestParam("rpNum") int rpNum,
                                @RequestParam("rpTable") String rpTable,
                                @RequestParam("boardNum") int boardNum) {
        adminReportService.processReport(rpTable, rpNum, boardNum);
        return "redirect:/admin/adminReport";
    }

    @GetMapping("/reportDetail")
    public String reportDetail(@RequestParam("rpNum") int rpNum,
                               Model model) {
        ReportDTO report = adminReportService.getReportById(rpNum);
        model.addAttribute("report", report);

        String table = report.getRpTable();
        int boardNum = report.getBoardNum(); // DTO에서 직접 꺼냄

        if ("community_board".equalsIgnoreCase(table)) {
            Community community = adminReportService.getCommunityPost(boardNum);
            model.addAttribute("post", community);
        } else if ("review_board".equalsIgnoreCase(table)) {
            Review review = adminReportService.getReviewPost(boardNum);
            model.addAttribute("post", review);
        }

        return "/admin/adminReportDetail";
    }
    
    // 상태 변환 처리
    @PostMapping("/cancelReport")
    public String cancelReport(@RequestParam("rpNum") int rpNum) {
        adminReportService.cancelReport(rpNum); // 상태 복원
        return "redirect:/admin/adminReport";
    }
}