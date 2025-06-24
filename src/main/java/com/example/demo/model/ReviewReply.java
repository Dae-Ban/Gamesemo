package com.example.demo.model;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("replyBoard")
public class ReviewReply {

    private int rbr_num;        // 댓글 번호
    private int rb_num;         // 글 번호 (리뷰 번호)
    private String id;          // 작성자 ID
    private String rbr_content; // 댓글 내용
    private Timestamp rbr_date; // 작성일
    private int rbr_state;      // 상태값 (0:정상 / 1:삭제)
}
