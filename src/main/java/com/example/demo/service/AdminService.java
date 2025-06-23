package com.example.demo.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AdminMapper;
import com.example.demo.model.Admin;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public boolean login(String adminId, String adminPw) {
        Admin admin = adminMapper.findByUsername(adminId);
        
        System.out.println("💡 전달된 raw adminId bytes: " + Arrays.toString(adminId.getBytes()));
        System.out.println("💡 DB에 있는 adminId와 비교 결과: " + "admin".equals(adminId));
        System.out.println("👉 [디버그] 조회된 admin: " + admin);
        System.out.println("💡 전달된 adminId: [" + adminId + "]");
        System.out.println("💡 전달된 adminPw: [" + adminPw + "]");

        if (admin == null) {
            System.out.println("❌ 사용자 없음");
            return false;
        }

        if (!admin.getAdminPw().equals(adminPw)) {
            System.out.println("❌ 비밀번호 불일치: 입력=" + adminPw + ", DB=" + admin.getAdminPw());
            return false;
        }

        System.out.println("✅ 로그인 성공");
        return true;
    }
}