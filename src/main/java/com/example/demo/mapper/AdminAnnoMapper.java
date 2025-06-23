package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.example.demo.model.AdminAnno;

public interface AdminAnnoMapper {

	// 공지사항 목록 + 검색 + 페이징
    List<AdminAnno> selectAnnoList(
        @Param("type") String type,
        @Param("keyword") String keyword,
        @Param("startRow") int startRow,
        @Param("endRow") int endRow
    );

    /// 총 개수
    int getTotal(@Param("type") String type, @Param("keyword") String keyword);

    // 상세 조회
    AdminAnno getAnnoDetail(@Param("anNum") int anNum);

    // 글 작성
	void insertAnno(AdminAnno anno);

	// 글 수정
	int updateAnno(AdminAnno anno);
	
	// 글 삭제
	void deleteAnno(@Param("anNum") int anNum);
}
