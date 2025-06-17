package com.example.demo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Report;

@Mapper
public interface ReportMapper {
    
    // 신고 등록
    void insertReport(Report report);

    // 중복 신고 여부 확인
    int checkDuplicateReport(Map<String, Object> map);
}
