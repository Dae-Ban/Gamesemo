package com.example.demo.model;

import org.apache.ibatis.type.Alias;
import lombok.Data;

@Data
@Alias("reviewLike")
public class ReviewLike {

    private int r_like;      // 프라이머리키 (PK)
    private String id;    // 추천한 사용자 아이디
    private int rb_num;   // 추천한 리뷰 글 번호
}
