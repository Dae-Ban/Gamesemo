package com.example.demo.controller;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Pagenation;
import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 목록
    @GetMapping("/list")
    public String list(
            @RequestParam(name = "page", defaultValue = "1") int page,
            Review review,
            Model model) {

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

    // 상세보기
    @GetMapping("/view")
    public String view(@RequestParam("rb_num") int rb_num, Model model) {
        reviewService.updateReadCount(rb_num);
        Review review = reviewService.getReview(rb_num);
        model.addAttribute("review", review);
        return "review/reviewcontent";
    }

    // 글쓰기 폼
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("review", new Review());
        return "review/reviewForm";
    }

    
 // 글 등록
    @PostMapping("/insert")
    public String insert(@ModelAttribute Review review) {
        // 임시 로그인 ID 설정
        review.setId("testuser");

        // 디버깅용 로그 출력
        System.out.println("선택된 추천/비추천 값: " + review.getRb_like());
        System.out.println("rb_like 확인: " + review.getRb_like());

        // 정말 아무것도 선택하지 않았을 때만 막음
        if (review.getRb_like() == null || review.getRb_like().trim().isEmpty()) {
            // 선택 안 하면 다시 폼으로 리디렉션 (나중에 alert 메시지도 처리 가능)
            return "redirect:/review/form?error=1";
        }

        reviewService.insert(review);
        return "redirect:/review/list";
    }
    //스마트 에디터 사진 업로드
    @RequestMapping(value="smarteditorMultiImageUpload")
    public void smarteditorMultiImageUpload(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("text/plain; charset=UTF-8");

            String sFilename = request.getHeader("file-name");
            String sFilenameExt = sFilename.substring(sFilename.lastIndexOf(".")+1).toLowerCase();

            String[] allowFileArr = {"jpg","png","bmp","gif"};
            boolean isImage = Arrays.asList(allowFileArr).contains(sFilenameExt);

            if (!isImage) {
                response.getWriter().print("NOTALLOW_" + sFilename);
                return;
            }

            // 업로드 실제 경로 설정
            String filePath = request.getServletContext().getRealPath("/upload/");
            File file = new File(filePath);
            if (!file.exists()) file.mkdirs();

            String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
            String sRealFileNm = today + UUID.randomUUID().toString() + sFilename.substring(sFilename.lastIndexOf("."));
            String rlFileNm = filePath + File.separator + sRealFileNm;

            // 파일 저장
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

            // 경로 전달
            String sFileInfo = "";
            sFileInfo += "&bNewLine=true";
            sFileInfo += "&sFileName=" + sFilename;
            sFileInfo += "&sFileURL=/upload/" + sRealFileNm;

            response.getWriter().print(sFileInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 글 수정 폼
    @GetMapping("/updateform")
    public String edit(@RequestParam("rb_num") int rb_num, Model model) {
        Review review = reviewService.getReview(rb_num);
        model.addAttribute("review", review);
        return "review/reviewupdateform";
    }

    // 글 수정 처리
    @PostMapping("/update")
    public String update(@ModelAttribute Review review) {
        reviewService.update(review);
        return "redirect:/review/view?rb_num=" + review.getRb_num();
    }

    // 글 삭제
    @GetMapping("/delete")
    public String delete(@RequestParam("rb_num") int rb_num) {
        reviewService.delete(rb_num);
        return "redirect:/review/list";
    }
   
}