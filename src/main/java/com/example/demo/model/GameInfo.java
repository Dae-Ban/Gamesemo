package com.example.demo.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameInfo {
    /** 고유 번호 (PK) */
    private Long giNum;

    /** 게임번호 (G_NUM) */
    private Long gNum;

    /** 플랫폼 (GI_PLATFORM) */
    private String giPlatform;

    /** 게임 타이틀 (GI_TITLE) */
    private String giTitle;

    /** 썸네일 URL (GI_THUMB) */
    private String giThumb;

    /** 원가 (GI_PRICE) */
    private Integer giPrice;

    /** 최종가 (GI_FPRICE) */
    private Integer giFprice;

    /** 할인율 (GI_RATE) */
    private Integer giRate;

    /** 링크 (GI_LINK) */
    private String giLink;

    /** 상태 (GI_STATE) */
    private String giState;

    /** 스크랩 날짜 (GI_DATE) */
    private LocalDateTime giDate;
}