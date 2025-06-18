package com.example.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    @Transactional
    public void cancelReport(int rpNum) {
    	// 1. 해당 신고 정보 조회
        ReportDTO report = reportMapper.selectReportById(rpNum);

        // 2. 신고 상태 'PENDING'으로 복원
        reportMapper.updateReportStatusToPending(rpNum);

        // 3. 게시글 상태도 복원 (RB_STATE 또는 CB_STATE = 0)
        String table = report.getRpTable();
        int boardNum = report.getBoardNum();

        if ("community_board".equalsIgnoreCase(table)) {
            reportMapper.unblindCommunityPost(boardNum); // 자유게시판 복원
        } else if ("review_board".equalsIgnoreCase(table)) {
            reportMapper.unblindReviewPost(boardNum); // 리뷰게시판 복원
        }
    }
    
    
}