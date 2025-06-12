package com.example.demo.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class Community {
	
	   private int cbNum;         // 글번호
	    private String id;         // 아이디
	    private String cbTitle;    // 제목
	    private String cbContent;  // 내용 (스마트에디터 포함 HTML)
	    private int cbReadcount;   // 조회수
	    private Timestamp cbDate;  // 날짜
	    private Integer cbState;   // 상태 (nullable)
}
