package com.example.demo.mapper;

import java.util.List;
import com.example.demo.model.ReportDTO;
import com.example.demo.model.Review;
import com.example.demo.model.Community;

public interface ReportMapper {
    
	List<ReportDTO> selectPagedReportList(int startRow, int endRow);
    
	int countReportTotal();
    
	ReportDTO selectReportById(int rpNum);
    
	Community selectCommunityPost(int boardNum);
    
	Review selectReviewPost(int boardNum);
    
	void updateReportStatus(int rpNum);
    
	void blindCommunityPost(int boardNum);
    
	void blindReviewPost(int boardNum);
}