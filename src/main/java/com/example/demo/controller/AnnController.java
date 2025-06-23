package com.example.demo.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Announcement;
import com.example.demo.service.AnnouncementService;

@Controller
@RequestMapping("announcement")
public class AnnController {

    @Autowired
    private AnnouncementService annService;

    @RequestMapping("announcementList")
    public String announcementList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) throws SQLException {

        int limit = 10;
        int start = (page - 1) * limit + 1;
        int end = page * limit;

        int listcount = (search != null && keyword != null && !keyword.trim().isEmpty())
                ? annService.countFiltered(search, keyword)
                : annService.count();

        List<Announcement> boardlist = annService.getAnnList(start, end, search, keyword);

        int pagecount = (int) Math.ceil((double) listcount / limit);
        int startpage = ((page - 1) / 10) * 10 + 1;
        int endpage = Math.min(startpage + 9, pagecount);

        model.addAttribute("page", page);
        model.addAttribute("search", search);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listcount", listcount);
        model.addAttribute("boardlist", boardlist);
        model.addAttribute("pagecount", pagecount);
        model.addAttribute("startpage", startpage);
        model.addAttribute("endpage", endpage);

        return "announcement/announcementList";
    }

    @RequestMapping("announcementContent")
    public String announcementContent(
            @RequestParam("no") int no,
            @RequestParam("page") String page,
            @RequestParam("state") int state,
            Model model) throws Exception {

        Announcement ann = annService.getContent(no);
        String AnnCont = ann.getAnContent().replace("\n", "<br>");

        if (state == 1) {
            model.addAttribute("ann", ann);
            model.addAttribute("page", page);
            model.addAttribute("AnnCont", AnnCont);
            return "announcement/announcementContent";
        } else {
            return "announcement/announcementList";
        }
    }
}
