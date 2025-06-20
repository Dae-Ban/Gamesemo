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
//            loginMember.setId("minjung2");    // 테스트용 기본값, 병합할때는 주석 막아야함
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
        
        List<Community> topList = communityService.getTopRecommended();
        model.addAttribute("topList", topList);


        return "community/communityList";
    }

    @GetMapping("/view")
    public String view(@RequestParam("cb_num") int cb_num, Model model, HttpSession session) {
        ensureLoginSession(session); 

        communityService.updateReadCount(cb_num);
        Community community = communityService.getCommunity(cb_num);
        List<CommunityReply> replylist = communityService.getReplyList(cb_num);
        
        int likeCount = communityService.getLikeCount(cb_num);

        model.addAttribute("community", community);
        model.addAttribute("replylist", replylist);
        model.addAttribute("likeCount", likeCount);
        
        return "community/communityView";
    }

    @GetMapping("/form")
    public String form(Model model, HttpSession session) {
        ensureLoginSession(session);
        model.addAttribute("community", new Community());
        return "community/communityForm";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Community community, 
    		HttpSession session,
    		RedirectAttributes rttr) {
        Member member = (Member) session.getAttribute("loginMember");
        community.setId(member.getId());
//        community.setId("minjung2");
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

    @PostMapping("/like")
    public String like(@RequestParam("cb_num") int cb_num, HttpSession session, RedirectAttributes ra) {
        Member loginMember = ensureLoginSession(session);
        CommunityLike like = new CommunityLike();
      like.setId(loginMember.getId());
//        like.setId("minjung2");
        like.setCb_num(cb_num);

        boolean isFirst = communityService.insertLike(like);
        if (isFirst) {
            ra.addFlashAttribute("msg", "추천 완료!");
        } else {
            ra.addFlashAttribute("msg", "이미 추천한 글입니다.");
        }

        ra.addAttribute("cb_num", cb_num);
        return "redirect:/community/view";
    }

    // ---------------------------------------------
    // 아래부터 댓글 관련 메서드
    // ---------------------------------------------

    @PostMapping("/reply/insert")
    public String insertReply(@ModelAttribute CommunityReply reply, HttpSession session, RedirectAttributes ra) {
        Member loginMember = (Member) session.getAttribute("loginMember");
      reply.setId(loginMember.getId());
//        reply.setId("minjung2");

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