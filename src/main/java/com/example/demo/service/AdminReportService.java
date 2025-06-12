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

    public ReportDTO getReportById(int rpNum) {
        return reportMapper.selectReportById(rpNum);
    }

    public Community getCommunityPost(int boardNum) {
        return reportMapper.selectCommunityPost(boardNum);
    }

    public Review getReviewPost(int boardNum) {
        return reportMapper.selectReviewPost(boardNum);
    }

    public void processReport(String rpTable, int rpNum, int boardNum) {
        reportMapper.updateReportStatus(rpNum);
        if ("FREE".equalsIgnoreCase(rpTable)) {
            reportMapper.blindCommunityPost(boardNum);
        } else if ("REVIEW".equalsIgnoreCase(rpTable)) {
            reportMapper.blindReviewPost(boardNum);
        }
    }
}