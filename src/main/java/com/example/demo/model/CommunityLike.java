package com.example.demo.model;

import org.apache.ibatis.type.Alias;
import lombok.Data;

@Data
@Alias("communityLike")
public class CommunityLike {

    private int c_like;      // 프라이머리키 (PK)
    private String id;    // 추천한 사용자 아이디
    private int cb_num;   // 추천한 커뮤니티 글 번호
}
