package com.example.demo.controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
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

@Controller
@RequestMapping("/reviewBoard")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

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

        return "reviewBoard/reviewList";
    }

    @GetMapping("/view")
    public String view(@RequestParam("rb_num") int rb_num, Model model) {
        reviewService.updateReadCount(rb_num);
        Review review = reviewService.getReview(rb_num);
        model.addAttribute("review", review);
        return "reviewBoard/reviewView";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("review", new Review());
        return "reviewBoard/reviewForm";
    }

    @PostMapping("/insert")
    public String insert(@ModelAttribute Review review) {
        review.setId("testuser");
        System.out.println("선택된 추천/비추천 값: " + review.getRb_like());

        if (review.getRb_like() == null || review.getRb_like().trim().isEmpty()) {
            return "redirect:/reviewBoard/form?error=1";
        }

        reviewService.insert(review);
        return "redirect:/reviewBoard/list";
    }

    @RequestMapping(value = "smarteditorMultiImageUpload")
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

            String filePath = request.getServletContext().getRealPath("/upload/");
            File file = new File(filePath);
            if (!file.exists()) file.mkdirs();

            String today = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
            String sRealFileNm = today + UUID.randomUUID().toString() + sFilename.substring(sFilename.lastIndexOf("."));
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

    @GetMapping("/updateform")
    public String edit(@RequestParam("rb_num") int rb_num, Model model) {
        Review review = reviewService.getReview(rb_num);
        model.addAttribute("review", review);
        return "reviewBoard/reviewUpdateForm";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Review review) {
        reviewService.update(review);
        return "redirect:/reviewBoard/view?rb_num=" + review.getRb_num();
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("rb_num") int rb_num) {
        reviewService.delete(rb_num);
        return "redirect:/reviewBoard/list";
    }
}