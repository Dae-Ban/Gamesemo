package com.example.demo.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.*;
import com.example.demo.service.CommunityService;
import com.example.demo.service.ReportService;

import jakarta.servlet.http.*;

@Controller
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

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

    @GetMapping("/list")
    public String list(@RequestParam(name = "page", defaultValue = "1") int page,
                       Community community, Model model) {
        int total = communityService.getCount(community);
        Pagenation pgn = new Pagenation(total, 10, page);
        pgn.setSearch(community.getSearch());
        pgn.setKeyword(community.getKeyword());

        List<Community> list = communityService.getPagedList(pgn);
        model.addAttribute("communityList", list);
        model.addAttribute("pgn", pgn);
        model.addAttribute("search", community.getSearch());
        model.addAttribute("keyword", community.getKeyword());

        return "community/communityList";
    }

    @GetMapping("/view")
    public String view(@RequestParam("cb_num") int cb_num, Model model, HttpSession session) {
        ensureLoginSession(session); 

        communityService.updateReadCount(cb_num);
        Community community = communityService.getCommunity(cb_num);
        List<CommunityReply> replylist = communityService.getReplyList(cb_num);

        model.addAttribute("community", community);
        model.addAttribute("replylist", replylist);
        return "community/communityView";
    }

    @GetMapping("/form")
    public String form(Model model, HttpSession session) {
        ensureLoginSession(session);
        model.addAttribute("community", new Community());
        return "community/communityForm";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Community community, HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");
//      community.setId(member.getId());
        community.setId("minjung1");
        community.setCb_state(0);

        communityService.insert(community);
        return "redirect:/community/list";
    }

    @GetMapping("/updateform")
    public String updateForm(@RequestParam("cb_num") int cb_num, Model model, HttpSession session) {
        ensureLoginSession(session);
        Community community = communityService.getCommunity(cb_num);
        model.addAttribute("community", community);
        return "community/communityUpdateForm";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Community community, HttpSession session) {
        ensureLoginSession(session);
        communityService.update(community);
        return "redirect:/community/view?cb_num=" + community.getCb_num();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("cb_num") int cb_num, HttpSession session, RedirectAttributes ra) {
        Member loginMember = ensureLoginSession(session);
        Community community = communityService.getCommunity(cb_num);

        if (community != null && community.getId().equals(loginMember.getId())) {
            communityService.delete(cb_num);
            ra.addFlashAttribute("msg", "삭제되었습니다.");
        } else {
            ra.addFlashAttribute("msg", "삭제 권한이 없습니다.");
        }

        return "redirect:/community/list";
    }

    @RequestMapping("/smarteditorMultiImageUpload")
    public void smarteditorMultiImageUpload(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/plain; charset=UTF-8");
            String sFilename = request.getHeader("file-name");
            String sFilenameExt = sFilename.substring(sFilename.lastIndexOf(".") + 1).toLowerCase();
            String[] allowFileArr = {"jpg", "png", "bmp", "gif"};
            boolean isImage = Arrays.asList(allowFileArr).contains(sFilenameExt);

            if (!isImage) {
                response.getWriter().print("NOTALLOW_" + sFilename);
                return;
            }

            String filePath = request.getServletContext().getRealPath("/upload/");
            File file = new File(filePath);
            if (!file.exists()) file.mkdirs();

            String today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
            String sRealFileNm = today + UUID.randomUUID() + sFilename.substring(sFilename.lastIndexOf("."));
            String rlFileNm = filePath + File.separator + sRealFileNm;

            InputStream inputStream = request.getInputStream();
            OutputStream outputStream = new FileOutputStream(rlFileNm);
            byte[] bytes = new byte[Integer.parseInt(request.getHeader("file-size"))];
            int numRead;
            while ((numRead = inputStream.read(bytes, 0, bytes.length)) != -1) {
                outputStream.write(bytes, 0, numRead);
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();

            String sFileInfo = "";
            sFileInfo += "&bNewLine=true";
            sFileInfo += "&sFileName=" + sFilename;
            sFileInfo += "&sFileURL=/upload/" + sRealFileNm;

            response.getWriter().print(sFileInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/report/form")
    public String reportForm(@RequestParam("rp_table") String rpTable,
                             @RequestParam("board_num") int boardNum,
                             Model model, HttpSession session) {
        ensureLoginSession(session);
        model.addAttribute("rp_table", rpTable);
        model.addAttribute("board_num", boardNum);
        return "report/reportForm";
    }

    @PostMapping("/report/insert")
    public String insertReport(@ModelAttribute Report report, HttpSession session, RedirectAttributes ra) {
        Member loginMember = ensureLoginSession(session);
        report.setId(loginMember.getId());
        report.setRpDate(new java.sql.Timestamp(System.currentTimeMillis()));
        report.setRpStatus("PENDING");

        if (reportService.existsByUserAndTarget(report)) {
            ra.addFlashAttribute("msg", "이미 신고한 게시글입니다.");
            return "redirect:/community/view?cb_num=" + report.getBoardNum();
        }

        reportService.insertReport(report);
        
        // 게시글 상태를 RESOLVED로 변경
        communityService.updateBoardState(report.getBoardNum(), 1);
        report.setRpStatus("RESOLVED");
        
        return "redirect:/community/view?cb_num=" + report.getBoardNum();
    }

    // ---------------------------------------------
    // 아래부터 댓글 관련 메서드 (추가된 부분)
    // ---------------------------------------------

    @PostMapping("/reply/insert")
    public String insertReply(@ModelAttribute CommunityReply reply, HttpSession session, RedirectAttributes ra) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        reply.setId("minjung1");

        int result = communityService.insertReply(reply);
        if(result==1) System.out.println("댓글 작성 성공");
        
        ra.addAttribute("cb_num", reply.getCb_num());
        return "redirect:/community/view";
    }

    @GetMapping("/reply/delete")   
    public String deleteReply(@RequestParam("cbr_num") int cbr_num,
                              @RequestParam("cb_num") int cb_num,
                              HttpSession session, RedirectAttributes ra) {
        ensureLoginSession(session);
        communityService.deleteReply(cbr_num);
        ra.addAttribute("cb_num", cb_num);
        return "redirect:/community/view";
    }
        
 // 댓글 수정 
    @GetMapping("/reply/update")
    public String replyupdate(@ModelAttribute CommunityReply reply, RedirectAttributes ra) {
        
       int result = communityService.replyupdate(reply);
       if(result == 1) System.out.println("댓글 수정 성공");
       
        ra.addAttribute("cb_num", reply.getCb_num());
        
         return "redirect:/community/view";
    }


}