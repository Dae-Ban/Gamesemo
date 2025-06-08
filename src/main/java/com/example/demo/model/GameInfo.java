package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class GameInfo {
    private int giNum;           // 게임 고유 번호 (PK)
    private int glNum;           // 게임 카테고리 또는 그룹 번호
    private String giPlatform;   // 플랫폼 (예: steam, nintendo)
    private String giTitle;      // 게임 제목
    private String giThumb;      // 썸네일 이미지 URL
    private int giPrice;         // 원래 가격
    private int giFprice;        // 할인된 가격
    private int giRate;          // 할인율
    private String giLink;       // 게임 링크 URL
    private String giState;      // 상태 (예: 활성, 비활성)
    private Timestamp giDate;    // 등록일
}