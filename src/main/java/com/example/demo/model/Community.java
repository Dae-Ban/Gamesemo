package com.example.demo.model;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;
import lombok.Data;

@Data
@Alias("communityBoard")
public class Community {

    private int cb_num;          // 글번호
    private String id;           // 아이디
    private String cb_title;     // 제목
    private String cb_content;   // 내용
    private int cb_readcount;    // 조회수
    private Timestamp cb_date;   // 날짜
    private int cb_state;        // 상태

    // 검색 관련 필드 (선택 사항)
    private String search;
    private String keyword;
}