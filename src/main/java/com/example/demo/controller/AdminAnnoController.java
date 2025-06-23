package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.AdminAnno;
import com.example.demo.page.AdminPagenation;
import com.example.demo.service.AdminAnnoService;

@Controller
@RequestMapping("/admin")
public class AdminAnnoController {

	@Autowired
	private AdminAnnoService adminAnnoService;

	// 공지사항 목록 + 검색
	@GetMapping("/adminAnno")
	public String annoList(@RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
	                       @RequestParam(name = "type", required = false) String type,
	                       @RequestParam(name = "keyword", required = false) String keyword,
	                       Model model) {

	    final int pageSize = 10;
	    final int blockSize = 10;

	    int total = adminAnnoService.getTotal(type, keyword);
	    AdminPagenation pagenation = new AdminPagenation(total, pageNum, pageSize, blockSize);

	    List<AdminAnno> annoList = adminAnnoService.getAnnoList(
	        type, keyword, pagenation.getStartRow(), pagenation.getEndRow()
	    );

	    int no = total - pagenation.getStartRow() + 1;

	    model.addAttribute("annoList", annoList);
	    model.addAttribute("no", no);
	    model.addAttribute("pageNum", pageNum);
	    model.addAttribute("total", total);
	    model.addAttribute("type", type);
	    model.addAttribute("keyword", keyword);

	    // 페이징 정보
	    model.addAttribute("pageCount", pagenation.getPageCount());
	    model.addAttribute("startPage", pagenation.getStartPage());
	    model.addAttribute("endPage", pagenation.getEndPage());
	    model.addAttribute("currentPage", pagenation.getCurrentPage());

	    return "admin/adminAnnoList";
	}

	// 공지사항 상세
	@GetMapping("/adminAnno/{anNum}")
	public String annoDetail(@PathVariable("anNum") int anNum, Model model) {
		AdminAnno anno = adminAnnoService.getAnnoDetail(anNum);
		model.addAttribute("anno", anno);
		return "admin/adminAnnoDetail"; // 상세 jsp 이름
	}
	
	// 글쓰기 폼 이동
	@GetMapping("/adminAnno/write")
	public String annoWriteForm() {
	    return "admin/adminAnnoWrite";  // 글쓰기 폼 jsp
	}
	
	// 글쓰기 처리
	@PostMapping("/adminAnno/write")
	public String insertAnno(AdminAnno anno) {
	    adminAnnoService.insertAnno(anno);
	    return "redirect:/admin/adminAnno"; // 작성 후 목록으로 이동
	}
	
	// 글수정 폼 이동
	@GetMapping("/adminAnno/update/{anNum}")
	public String annoUpdatForm(@PathVariable("anNum") int anNum, Model model) {
		AdminAnno anno = adminAnnoService.getAnnoDetail(anNum);
		model.addAttribute("anno", anno);
		return "admin/adminAnnoUpdate"; // 수정폼 jsp 이름
	}
	
	// 글수정 처리
	@PostMapping("/adminAnno/update")
	public String udateAnno(AdminAnno anno) {
		adminAnnoService.updateAnno(anno);
		return "redirect:/admin/adminAnno";
	}
	
	// 글 삭제 처리
	@GetMapping("/adminAnno/delete/{anNum}")
	public String deleteAnno(@PathVariable("anNum") int anNum) {
	    adminAnnoService.deleteAnno(anNum);
	    return "redirect:/admin/adminAnno";
	}
}
