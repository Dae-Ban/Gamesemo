package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Pagenation;
import com.example.demo.model.Review;
import com.example.demo.model.ReviewReply;

@Mapper
public interface ReviewMapper {

    int getCount(Review review);                  // 검색 조건용
    List<Review> getPagedList(Pagenation pgn);    // 페이징 리스트
    void updateReadCount(int rb_num);             // 조회수 증가
    Review getReview(int rb_num);                 // 상세 조회
    void insert(Review review);                   // 글 등록
    void update(Review review);                   // 글 수정
    void delete(int rb_num);                      // 글 삭제

    // 댓글 관련
    int insertReply(ReviewReply reply);          // 댓글 등록
    List<ReviewReply> getReplyList(int rb_num);   // 댓글 목록 조회
    void deleteReply(int rbr_num);                // 댓글 삭제 추가
	int replyupdate(ReviewReply reply);
	
		
}
