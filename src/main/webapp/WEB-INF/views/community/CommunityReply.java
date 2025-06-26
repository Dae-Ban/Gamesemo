package com.example.demo.model;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("communityReply")
public class CommunityReply {

    private int cbr_num;        // 댓글 번호
    private int cb_num;         // 글 번호 (리뷰 번호)
    private String id;          // 작성자 ID
    private String cbr_content; // 댓글 내용
    private Timestamp cbr_date; // 작성일
    private int cbr_state;      // 상태값 (0:정상 / 1:삭제)
}
