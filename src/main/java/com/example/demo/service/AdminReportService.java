package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.mapper.ReportMapper;
import com.example.demo.model.ReportDTO;
import com.example.demo.model.Review;
import com.example.demo.model.Community;

@Service
public class AdminReportService {

    @Autowired
    private ReportMapper reportMapper;

    // 페이지 처리
    public List<ReportDTO> getPagedReportList(int startRow, int endRow) {
        return reportMapper.selectPagedReportList(startRow, endRow);
    }

    // 총 갯수
    public int getTotalReportCount() {
        return reportMapper.countReportTotal();
    }

    // 상세 조회
    public ReportDTO getReportById(int rpNum) {
        return reportMapper.selectReportById(rpNum);
    }

    // 자유게시판 게시글 상세
    public Community getCommunityPost(int boardNum) {
        return reportMapper.selectCommunityPost(boardNum);
    }

    // 리뷰게시판 게시글 상세
    public Review getReviewPost(int boardNum) {
        return reportMapper.selectReviewPost(boardNum);
    }

    // 상태변경 + 자유 게시판 블라인드 + 리뷰게시판 블라인드
    public void processReport(String rpTable, int rpNum, int boardNum) {
        reportMapper.updateReportStatus(rpNum);
        if ("community_board".equalsIgnoreCase(rpTable)) {
            reportMapper.blindCommunityPost(boardNum);
        } else if ("review_board".equalsIgnoreCase(rpTable)) {
            reportMapper.blindReviewPost(boardNum);
        }
    }
    
    public void cancelReport(int rpNum) {
        reportMapper.updateReportStatusToPending(rpNum);
    }
}