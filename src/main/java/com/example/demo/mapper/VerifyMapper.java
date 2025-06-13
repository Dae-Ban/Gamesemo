package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

	@Select("SELECT * FROM MEMBER WHERE email_id = #{emailId} AND email_domain = #{domain}")
	public Member findIdMember(@Param("emailId") String emailId, @Param("domain") String domain);

}
