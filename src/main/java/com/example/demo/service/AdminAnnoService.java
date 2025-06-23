package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AdminAnnoMapper;
import com.example.demo.model.AdminAnno;

@Service
public class AdminAnnoService {
	   @Autowired
	    private AdminAnnoMapper adminAnnoMapper;

	    // 목록 + 검색 + 페이징
	    public List<AdminAnno> getAnnoList(String type, String keyword, int startRow, int endRow) {
	        return adminAnnoMapper.selectAnnoList(type, keyword, startRow, endRow);
	    }

	    // 총 개수
	    public int getTotal(String type, String keyword) {
	        return adminAnnoMapper.getTotal(type, keyword);
	    }

	    // 글 상세 조회
	    public AdminAnno getAnnoDetail(int anNum) {
	        return adminAnnoMapper.getAnnoDetail(anNum);
	    }

	    // 글 입력
		public void insertAnno(AdminAnno anno) {
			 adminAnnoMapper.insertAnno(anno);
			
		}

		// 글 수정
		public Object updateAnno(AdminAnno anno) {
			return adminAnnoMapper.updateAnno(anno);
		}

	

		// 글 삭제
		public void deleteAnno(int anNum) {
		    adminAnnoMapper.deleteAnno(anNum);
		}
}
