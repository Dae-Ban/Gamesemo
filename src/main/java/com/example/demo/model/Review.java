package com.example.demo.model;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;
import lombok.Data;

@Data
@Alias("reviewBoard")
public class Review {

    private int rb_num;           // 글번호
    private String id;            // 아이디
    private String rb_title;      // 제목
    private int rb_readcount;     // 조회수
    private Timestamp rb_date;    // 날짜
    private int rb_state;         // 상태
    private String rb_like;       // 리뷰 추천
    private String rb_content;    // 내용 (CLOB)
    private String nickname;
     
    private String search;
    private String keyword;
}