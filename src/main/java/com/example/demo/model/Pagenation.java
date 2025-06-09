package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.Getter;

@Getter
@Alias("pgn")
public class Pagenation {
	private int total;				// 데이터 갯수
	private int rowPerPage;			// 화면에 출력할 데이터 갯수
	private int pagePerBlk = 10;    // 블럭당 페이지 갯수 (1개의 블럭당 10개의 페이지)
	private int currentPage;		// 현재 페이지 번호
	private int startPage;			// 각 블럭의 시작 페이지
	private int endPage;            // 각 블럭의 끝 페이지
	private int totalPage;			// 총 페이지 수
	// page
	private int startRow;
	private int endRow;
	// 검색
	private String search;
	private String keyword;
	
	private int numStart;

	public Pagenation(int total, int rowPerPage, int currentPage) {
		this.total = total;
		this.rowPerPage = rowPerPage;
		this.currentPage = currentPage;
		
		startRow = (currentPage - 1) * rowPerPage + 1;
		endRow = startRow + rowPerPage - 1;
		totalPage = (int) Math.ceil((double) total / rowPerPage);
		startPage = currentPage - (currentPage - 1) % pagePerBlk;	// 1,  11, 21...
							// 10, 20, 30...
		numStart = total - startRow + 1;
		endPage = startPage + pagePerBlk - 1;
		if (endPage > totalPage)
			endPage = totalPage;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}