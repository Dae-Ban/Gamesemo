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
@RequestMapping("ann")
public class AnnController {
	
	@Autowired
	AnnouncementService annService;

	@RequestMapping("/announcement")
	public String announcementList(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "keyword", required = false) String keyword, Model model) throws SQLException {

		int limit = 10; // 한 페이지에 출력할 데이터 갯수
		int listcount = annService.count();
//	    System.out.println("listcount: " + listcount);

		List<Announcement> boardlist = annService.getAnnList(page, search, keyword);
//	    System.out.println("boardlist: " + boardlist);

		// 총페이지수 계산
		int pagecount = (int) Math.ceil((double) listcount / limit);

		// 페이지 네비게이션 계산
		int startpage = ((page - 1) / 10) * 10 + 1;
		int endpage = startpage + 10 - 1;
		if (endpage > pagecount)
			endpage = pagecount;

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
	public String announcementContent(@RequestParam("no") int no, 
									  @RequestParam("page") String page,
									  @RequestParam("state") int state, 
									  Model model) throws Exception {
		
		Announcement ann = annService.getContent(no);
		String AnnCont = ann.getAnContent().replace("\n","<br>");
		
		if(state == 1) {
			model.addAttribute("ann", ann);
			model.addAttribute("page", page);
			model.addAttribute("AnnCont", AnnCont);
		}else {
			return "announcement/announcementList";		
		}
			
		return "announcement/announcementContent";
	}

}