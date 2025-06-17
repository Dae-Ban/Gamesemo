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
import com.example.demo.service.ReviewService;
import com.example.demo.service.ReportService;

import jakarta.servlet.http.*;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReportService reportService;

    private Member ensureLoginSession(HttpSession session) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null) {
            loginMember = new Member();
            loginMember.setId("test1"); 	// 테스트용 기본값
            session.setAttribute("loginMember", loginMember);
        }
        return loginMember;
    }

    @GetMapping("/list")
    public String list(@RequestParam(name = "page", defaultValue = "1") int page,
                       Review review, Model model) {
        int total = reviewService.getCount(review);
        Pagenation pgn = new Pagenation(total, 10, page);
        pgn.setSearch(review.getSearch());
        pgn.setKeyword(review.getKeyword());

        List<Review> list = reviewService.getPagedList(pgn);
        model.addAttribute("reviewList", list);
        model.addAttribute("pgn", pgn);
        model.addAttribute("search", review.getSearch());
        model.addAttribute("keyword", review.getKeyword());

        return "review/reviewList";
    }

    @GetMapping("/view")
    public String view(@RequestParam("rb_num") int rb_num, Model model, HttpSession session) {
        ensureLoginSession(session); 

        reviewService.updateReadCount(rb_num);
        Review review = reviewService.getReview(rb_num);
        List<ReviewReply> replylist = reviewService.getReplyList(rb_num);

        model.addAttribute("review", review);
        model.addAttribute("replylist", replylist);
        return "review/reviewView";
    }

    @GetMapping("/form")
    public String form(Model model, HttpSession session) {
        ensureLoginSession(session);
        model.addAttribute("review", new Review());
        return "review/reviewForm";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Review review, HttpSession session) {
        Member member = (Member) session.getAttribute("loginMember");
        review.setId("test1");
        review.setRb_state(0);

        reviewService.insert(review);
        return "redirect:/review/list";
    }

    @GetMapping("/updateform")
    public String updateForm(@RequestParam("rb_num") int rb_num, Model model, HttpSession session) {
        ensureLoginSession(session);
        Review review = reviewService.getReview(rb_num);
        model.addAttribute("review", review);
        return "review/reviewUpdateForm";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Review review, HttpSession session) {
        ensureLoginSession(session);
        reviewService.update(review);
        return "redirect:/review/view?rb_num=" + review.getRb_num();
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("rb_num") int rb_num, HttpSession session, RedirectAttributes ra) {
        Member loginMember = ensureLoginSession(session);
        Review review = reviewService.getReview(rb_num);

        if (review != null && review.getId().equals(loginMember.getId())) {
            reviewService.delete(rb_num);
            ra.addFlashAttribute("msg", "삭제되었습니다.");
        } else {
            ra.addFlashAttribute("msg", "삭제 권한이 없습니다.");
        }

        return "redirect:/review/list";
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

            String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
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
        report.setRpStatus("대기");

        if (reportService.existsByUserAndTarget(report)) {
            ra.addFlashAttribute("msg", "이미 신고한 게시글입니다.");
            return "redirect:/review/view?rb_num=" + report.getBoardNum();
        }

        reportService.insertReport(report);
        return "redirect:/review/view?rb_num=" + report.getBoardNum();
    }

    // ---------------------------------------------
    // 아래부터 댓글 관련 메서드 (추가된 부분)
    // ---------------------------------------------

    @PostMapping("/reply/insert")
    public String insertReply(@ModelAttribute ReviewReply reply, HttpSession session, RedirectAttributes ra) {
        Member loginMember = (Member) session.getAttribute("loginMember");
        reply.setId("test1");

        int result = reviewService.insertReply(reply);
        if(result==1) System.out.println("댓글 작성 성공");
        
        ra.addAttribute("rb_num", reply.getRb_num());
        return "redirect:/review/view";
    }

    @GetMapping("/reply/delete")   
    public String deleteReply(@RequestParam("rbr_num") int rbr_num,
                              @RequestParam("rb_num") int rb_num,
                              HttpSession session, RedirectAttributes ra) {
        ensureLoginSession(session);
        reviewService.deleteReply(rbr_num);
        ra.addAttribute("rb_num", rb_num);
        return "redirect:/review/view";
    }
        
 // 댓글 수정 
    @GetMapping("/reply/update")
    public String replyupdate(@ModelAttribute ReviewReply reply, RedirectAttributes ra) {
        
    	int result = reviewService.replyupdate(reply);
    	if(result == 1) System.out.println("댓글 수정 성공");
    	
    	 ra.addAttribute("rb_num", reply.getRb_num());
    	 
         return "redirect:/review/view";
    }


}
