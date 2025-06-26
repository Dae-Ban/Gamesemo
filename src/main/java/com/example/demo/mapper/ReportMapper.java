package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Community;
import com.example.demo.model.Report;
import com.example.demo.model.ReportDTO;
import com.example.demo.model.Review;

@Mapper
public interface ReportMapper {
    
	List<ReportDTO> getPagedReportList(@Param("type") String type,
            @Param("keyword") String keyword,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow);
    
	int getTotalReportCount(@Param("type") String type,
            @Param("keyword") String keyword);
    
	ReportDTO selectReportById(int rpNum);
     
	Community selectCommunityPost(int boardNum);
    
	Review selectReviewPost(int boardNum);
    
	void updateReportStatus(int rpNum);
    
	void blindCommunityPost(int boardNum);
    
	void blindReviewPost(int boardNum);

	void updateReportStatusToPending(int rpNum);
	
	void unblindCommunityPost(int boardNum);

	void unblindReviewPost(int boardNum);
	
    // 신고 등록
    void insertReport(Report report);

    // 중복 신고 여부 확인
    int checkDuplicateReport(Map<String, Object> map);
}
