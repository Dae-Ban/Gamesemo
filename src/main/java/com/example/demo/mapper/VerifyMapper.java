package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.AccountVerification;
import com.example.demo.model.Member;

@Mapper
public interface VerifyMapper {


	public Member getTestMember();

	public AccountVerification findByCode(String code);

	public int updateEmailVerified(String token);

	public void insertTestMember(Member member);

	public void insertVerification(AccountVerification verification);

	public int updateVerificationTable(String code);

}
