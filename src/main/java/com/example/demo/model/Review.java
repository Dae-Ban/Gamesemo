package com.example.demo.model;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;
import lombok.Data;

@Data
@Alias("review")
public class Review {

    private int rb_num;
    private String id;
    private String rb_title;
    private String rb_content;
    private int rb_readcount;
    private Timestamp rb_date;
    private int rb_state;
    private String rb_like;
    
    private String search;
    private String keyword;
    
}