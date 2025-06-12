package com.example.demo.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class Review {
	  private int rbNum;          // 글번호
	    private String id;          // 아이디
	    private String col;         // 닉네임 또는 컬럼
	    private String rbTitle;     // 제목
	    private String rbContent;   // 내용
	    private int rbReadcount;    // 조회수
	    private Timestamp rbDate;   // 날짜
	    private int rbState;        // 상태
}
