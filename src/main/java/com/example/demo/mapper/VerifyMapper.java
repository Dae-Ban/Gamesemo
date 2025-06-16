package com.example.demo.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.AccountVerification;
import com.example.demo.model.Member;

@Mapper
public interface VerifyMapper {


	public Member getTestMember();

	AccountVerification findByCode(@Param("code") String code, @Param("type") String type);

	public int updateEmailVerified(String token);

	public void insertTestMember(Member member);

	public void insertVerification(AccountVerification verification);

	public int updateVerificationTable(@Param("code") String code, @Param("type") String type);

	public Member findIdMember(@Param("email")String email);

	public boolean isEmailExist(@Param("email") String email);

	AccountVerification findByEmailAndType(@Param("email") String email, @Param("type") String type);

	public AccountVerification findByIdAndType(Map<String, Object> map);

}
