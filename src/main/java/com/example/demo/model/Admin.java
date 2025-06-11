package com.example.demo.model;

import lombok.Data;

@Data
public class Admin {
    private String adminId;  // DB 컬럼 admin_id와 매핑
    private String adminPw;  // DB 컬럼 admin_pw와 매핑
}