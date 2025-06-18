package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.WishlistMapper;

@Service
public class WishlistService {
	
	@Autowired
	WishlistMapper wishlistMapper;

	public int addWishlist(String gNum, String id) {
		return wishlistMapper.addWishlist(gNum,id);
	}

	



	
	
}
