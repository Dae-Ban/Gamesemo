package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Pagenation;
import com.example.demo.model.Community;
import com.example.demo.model.CommunityReply;

@Mapper
public interface CommunityMapper {

    int getCount(Community community);                  // 검색 조건용
    List<Community> getPagedList(Pagenation pgn);    // 페이징 리스트
    void updateReadCount(int cb_num);             // 조회수 증가
    Community getCommunity(int cb_num);                 // 상세 조회
    void insert(Community community);                   // 글 등록
    void update(Community community);                   // 글 수정
    void delete(int cb_num);                      // 글 삭제

    // 댓글 관련
    int insertReply(CommunityReply reply);          // 댓글 등록
    List<CommunityReply> getReplyList(int cb_num);   // 댓글 목록 조회
    void deleteReply(int cbr_num);                // 댓글 삭제 추가
	int replyupdate(CommunityReply reply);
	
}
