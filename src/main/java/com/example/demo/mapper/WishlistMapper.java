package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WishlistMapper {

	int addWishlist(@Param("gNum")String gNum, @Param("id")String id);

	
	
	
	
}
