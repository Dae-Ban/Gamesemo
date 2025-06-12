package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Member;

@Mapper
public interface VerifyMapper {


	public Member getTestMember();

	public Member findByToken(String token);

	public int updateEmailVerified(String token);

	public void insertTestMember(Member member);

}
