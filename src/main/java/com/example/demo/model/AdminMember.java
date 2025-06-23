package com.example.demo.model;

import lombok.Data;

@Data
public class AdminMember {
	    private String id;
	    private String name;
	    private String emailId;
	    private String emailDomain;
	    private int state;
	    
	    // 페이징 및 검색용
	    private int startRow;
	    private int endRow;
	    private String type;   // name, email, state
	    private String keyword;
	    
}
