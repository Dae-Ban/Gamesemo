package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ReviewMapper;
import com.example.demo.model.Pagenation;
import com.example.demo.model.Review;
import com.example.demo.model.ReviewReply;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    public int getCount(Review review) {
        return reviewMapper.getCount(review);
    }

    public List<Review> getPagedList(Pagenation pgn) {
        return reviewMapper.getPagedList(pgn);
    }

    public void updateReadCount(int rb_num) {
        reviewMapper.updateReadCount(rb_num);
    }

    public Review getReview(int rb_num) {
        return reviewMapper.getReview(rb_num);
    }

    public void insert(Review review) {
        reviewMapper.insert(review);
    }

    public void update(Review review) {
        reviewMapper.update(review);
    }

    public void delete(int rb_num) {
        reviewMapper.delete(rb_num);
    }

    // 댓글 등록
    public int insertReply(ReviewReply reply) {
    	return reviewMapper.insertReply(reply);
    }

    // 댓글 목록 가져오기
    public List<ReviewReply> getReplyList(int rb_num) {
        return reviewMapper.getReplyList(rb_num);
    }

    // 댓글 삭제
    public void deleteReply(int rbr_num) {
        reviewMapper.deleteReply(rbr_num);
    }
    //댓글 수정
//    public ReviewReply getReply(int rbr_num) {
//        return reviewMapper.getReply(rbr_num);
//    }

	public int replyupdate(ReviewReply reply) {
		return reviewMapper.replyupdate(reply);
	}

}
