package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
    Member findByIdAndPw(@Param("id") String id, @Param("pw") String pw);

	Member findBySocialIdAndPlatform(String socialId, String platform);

	void insert(Member member);
}
