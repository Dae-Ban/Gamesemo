package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.Wishlist;

@Mapper
public interface WishlistMapper {

	int addWishlist(Wishlist wishlist);

	Integer countByUserAndGame(@Param("gNum") int gnum, @Param("id") String id);

}
