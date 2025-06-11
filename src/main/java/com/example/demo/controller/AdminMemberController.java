package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.AdminMember;
import com.example.demo.service.AdminMemberService;

@Controller
@RequestMapping("/admin")
public class AdminMemberController {

    @Autowired
    private AdminMemberService adminMemberService;

    // 회원 목록 출력 + 검색
    @GetMapping("/adminMember")
    public String showMemberList(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
                                 @RequestParam(name = "type", required = false) String type,
                                 @RequestParam(name = "keyword", required = false) String keyword,
                                 Model model) {

        final int rowPerPage = 10;
        int total = adminMemberService.getTotal(type, keyword);
        int startRow = (pageNum - 1) * rowPerPage + 1;
        int endRow = startRow + rowPerPage - 1;

        List<AdminMember> memberList = adminMemberService.pagingSearch(type, keyword, startRow, endRow);
        int no = total - startRow + 1;

        model.addAttribute("memberList", memberList);
        model.addAttribute("no", no);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("total", total);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);

        return "adminMember";
    }
    
    // 회원 상태 업데이트
    @PostMapping("/updateState")
    @ResponseBody
    public ResponseEntity<String> updateMemberState(@RequestParam("id") String id,
            @RequestParam("state") int state) {
        int result = adminMemberService.updateState(id, state);
        return result > 0 ? ResponseEntity.ok("success") : ResponseEntity.status(500).body("fail");
    }
}