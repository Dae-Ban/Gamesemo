package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReportDTO {
    private int rpNum;
    private String id;
    private String rpReason;
    private Timestamp rpDate;
    private String rpTable;
    private int boardNum;
    private String rpStatus;
    
    private String postTitle; // 게시글 제목 (조인용)
    
}