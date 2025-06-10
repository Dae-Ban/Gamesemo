package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.AdminMapper;
import com.example.demo.model.Admin;


@Service
public class AdminService {

	@Autowired
    private AdminMapper adminMapper;

    public boolean login(String admin_id, String admin_pw) {
        Admin admin = adminMapper.findByUsername(admin_id);
        System.out.println("👉 [디버그] 조회된 admin: " + admin);
        System.out.println("💡 전달된 admin_id: [" + admin_id + "]");
        System.out.println("💡 전달된 admin_pw: [" + admin_pw + "]");

        if (admin == null) {
            System.out.println("❌ 사용자 없음");
            return false;
        }

        if (!admin.getAdmin_pw().equals(admin_pw)) {
            System.out.println("❌ 비밀번호 불일치: 입력=" + admin_id + ", DB=" + admin.getAdmin_pw());
            return false;
        }

        System.out.println("✅ 로그인 성공");
        return true;
    }
}
