package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ReviewMapper;
import com.example.demo.model.Pagenation;
import com.example.demo.model.Review;

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
}
