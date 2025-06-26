package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ReviewMapper;
import com.example.demo.model.Community;
import com.example.demo.model.CommunityLike;
import com.example.demo.model.Pagenation;
import com.example.demo.model.Review;
import com.example.demo.model.ReviewLike;
import com.example.demo.model.ReviewReply;

@Service
public class ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    public int getCount(Review review) {
        System.out.println("리뷰 카운트 호출");
        try {
            int i = reviewMapper.getCount(review);
            System.out.println("리뷰 카운트 리턴");
            return i;
        } catch (Exception e) {
            e.printStackTrace(); // 어떤 예외인지 콘솔에 출력
            return 0;
        }
    }

 
    public List<Review> getPagedList(Pagenation pgn) {
    	System.out.println("리뷰 리스트");
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
	public int replyupdate(ReviewReply reply) {
		return reviewMapper.replyupdate(reply);
	}
	
	//글 추천수 4개 출력
		public List<Review> getTopRecommended() {
			System.out.println("리뷰서비스호출");
		    return reviewMapper.getTopRecommended();
		}
		
		//글 추천 추가
		public boolean insertLike(ReviewLike like) {
		    ReviewLike existing = reviewMapper.checkAlreadyLiked(like);
		    if (existing == null) {
		        return reviewMapper.insertLike(like) == 1;
		    }
		    return false;
		}
		
		//글 추천한거 가져오기
		public int getLikeCount(int rb_num) {
		    return reviewMapper.getLikeCount(rb_num);
		}
	
}
