package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Member;

@Mapper
public interface MemberMapper {
    List<Member> selectAllEmailSubscribers();
}
