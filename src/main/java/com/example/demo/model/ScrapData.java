package com.example.demo.model;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("scrap")
public class ScrapData {
	private int pk;           // PK
    private String title;      // 게임 제목
    private String thumb;      // 썸네일 이미지 URL
    private String price;         // 원래 가격
    private String fprice;        // 할인된 가격
    private String rate;          // 할인율
    private String link;       // 게임 링크 URL
    private Timestamp scrapedAt;    // 등록일
    private String nTitle; // 제목 정규화
}
