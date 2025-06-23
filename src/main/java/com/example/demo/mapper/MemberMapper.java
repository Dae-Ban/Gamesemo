package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
    Member findById(String id);

	Member findByEmail(@Param("emailId") String emailId, @Param ("emailDomain") String emailDomain);

	void insert(Member member);

	void socialInsert(Member member);
}	
