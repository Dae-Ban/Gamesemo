
package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
    Member findById(String id);

	Member findBySocialIdAndPlatform(String socialId, String platform);

	void insert(Member member);
}	
