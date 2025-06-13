package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Pagenation;
import com.example.demo.model.Review;

@Mapper
public interface ReviewMapper {
    int getCount(Review review);
    List<Review> getPagedList(Pagenation pgn);
    void updateReadCount(int cb_num);
    Review getReview(int cb_num);
    void insert(Review review);
    void update(Review review);
    void delete(int cb_num);
}