package com.example.demo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Member {
    private String id;                // ID (PK)
    private String name;              // 이름
    private String nickname;          // 닉네임
    private String pw;                // 비밀번호
    private String birthDate;         // 생년월일
    private String emailId;           // 이메일 아이디
    private String emailDomain;       // 이메일 도메인
    private String socialPlatform;    // 소셜 로그인 플랫폼
    private String socialId;          // 소셜 로그인 ID
    private String phone;             // 전화번호
    private String gender;            // 성별
    private Timestamp joinDate;       // 가입일
    private int state;                // 상태값 (활성/비활성 등)
    private String emailAd;           // 이메일 광고 수신 동의 여부
    private String emailVerified;     // 이메일 인증 여부
    
//   이메일 인증용 토큰 필드는 테이블 새로 만듦.
//	private String VerificationToken;
		
		
}
