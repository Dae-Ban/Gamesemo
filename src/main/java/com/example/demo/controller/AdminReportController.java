package com.example.demo.controller;

import com.example.demo.model.ReportDTO;
import com.example.demo.model.Review;
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
    public String reportList(Model model, @RequestParam(defaultValue = "1") int page) {
        int limit = 10;
        int startRow = (page - 1) * limit + 1;
        int endRow = page * limit;

        List<ReportDTO> list = adminReportService.getPagedReportList(startRow, endRow);
        int total = adminReportService.getTotalReportCount();
        int totalPage = (int)Math.ceil((double)total / limit);

        model.addAttribute("reportList", list);
        model.addAttribute("page", page);
        model.addAttribute("totalPage", totalPage);

        return "adminReport";
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
                                @RequestParam("rpTable") String rpTable,
                                @RequestParam("boardNum") int boardNum,
                                Model model) {

        ReportDTO report = adminReportService.getReportById(rpNum);
        model.addAttribute("report", report);

        if ("Community".equalsIgnoreCase(rpTable)) {
        	Community community = adminReportService.getCommunityPost(boardNum);
            model.addAttribute("post", community);
        } else if ("REVIEW".equalsIgnoreCase(rpTable)) {
            Review review = adminReportService.getReviewPost(boardNum);
            model.addAttribute("post", review);
        }

        return "adminReportDetail";
    }
}