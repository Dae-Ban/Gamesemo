package com.example.demo.controller;

import com.example.demo.mapper.WishlistMapper;
import com.example.demo.model.GameInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistMapper wishlistMapper;

    @GetMapping("")
    public String viewWishlist(@RequestParam(name = "keyword", required = false) String keyword,
                               @RequestParam(name = "order", defaultValue = "recent") String order,
                               @RequestParam(name = "page", defaultValue = "1") int page,
                               HttpSession session, Model model) {

        String id = (String) session.getAttribute("id");
        if (id == null) {
            session.setAttribute("redirectAfterLogin", "/wishlist");
            return "redirect:/member/login";
        }

        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        int total = wishlistMapper.countWishlist(id, keyword);
        List<GameInfo> wishlist = (keyword != null && !keyword.trim().isEmpty())
                ? wishlistMapper.searchWishlistPaged(id, "%" + keyword + "%", order, offset, pageSize)
                : wishlistMapper.getWishlistPaged(id, order, offset, pageSize);

        int totalPages = (int) Math.ceil((double) total / pageSize);

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("keyword", keyword);
        model.addAttribute("order", order);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "wishlist/wishList";
    }

    @PostMapping("/add")
    public String add(@RequestParam(name ="gNum") Long gNum,
                      @RequestParam(name = "page", defaultValue = "1") int page,
                      HttpSession session) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            session.setAttribute("redirectAfterLogin", "/wishlist");
            return "redirect:/member/login";
        }
        if (id != null && !wishlistMapper.isGameInWishlist(id, gNum)) {
            wishlistMapper.addToWishlist(id, gNum);
        }
        return "redirect:/game?page=" + page;
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam (name="gNum") Long gNum, HttpSession session) {
        String id = getLoginId(session);
        if (id != null) {
            wishlistMapper.removeFromWishlist(id, gNum);
            return ResponseEntity.ok("삭제 성공");
        }
        return ResponseEntity.badRequest().body("로그인 필요");
    }

    private String getLoginId(HttpSession session) {
        return (String) session.getAttribute("id");
    }
}