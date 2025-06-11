package com.example.demo.service;

import com.example.demo.model.Member;

public interface MemberService {

	Member login(String id, String pw);

	Member findBySocialIdAndPlatform(String socialId, String platform);

	void insertMember(Member member);
	
}
