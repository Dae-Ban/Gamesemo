package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ReportMapper;
import com.example.demo.model.Report;

@Service
public class ReportService {

    @Autowired
    private ReportMapper reportMapper;

    // 신고 등록
    public void insertReport(Report report) {
        reportMapper.insertReport(report);
    }

    // 중복 신고 여부 확인
    public boolean existsByUserAndTarget(Report report) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", report.getId());
        map.put("rp_table", report.getRp_table());
        map.put("board_num", report.getBoard_num());

        return reportMapper.checkDuplicateReport(map) > 0;
    }

    
}
