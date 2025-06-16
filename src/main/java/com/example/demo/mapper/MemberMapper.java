package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
	
    List<Member> selectAllEmailSubscribers();

	void insertMember(Member member);

	 Member findByEmail(@Param("email") String email); 
	 
}
