
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
	private int startRow;			// 시작 번호
	private int endRow;				// 마지막 번호
	// 검색
	private String search;
	private String keyword;
	// 게임 필터링
	private String giState;
	private String giPlatform;
	private String sort;	// 정렬
	
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

	public void setGiState(String giState) {
		this.giState = giState;
	}

	public void setGiPlatform(String giPlatform) {
		this.giPlatform = giPlatform;
	}

	
	public void setSort(String sort) {
		this.sort = sort;
	}
	
}
