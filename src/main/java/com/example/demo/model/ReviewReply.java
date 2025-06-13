package com.example.demo.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class ReviewReply {

    private int reply_num;        // 댓글 번호 (PK)
    private int rb_num;           // 리뷰 번호 (FK, 댓글이 속한 글)
    private String id;            // 작성자 ID
    private String content;       // 댓글 내용
    private Timestamp regdate;    // 작성일
    private int state;            // 상태값 (정상/삭제 등)
}