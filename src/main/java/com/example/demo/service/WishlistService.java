package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.WishlistMapper;
import com.example.demo.model.Wishlist;

@Service
public class WishlistService {
	
	@Autowired
	WishlistMapper wishlistMapper;
	

	public int addWishlist(Wishlist wishlist) {
		if (exists(wishlist.getGNum(), wishlist.getId())) {
            return 0;
        }
        return wishlistMapper.addWishlist(wishlist); 
    }
	
	/** true if this user already wishlisted this game */
    public boolean exists(int gnum, String userId) {
        return wishlistMapper.countByUserAndGame(gnum, userId) > 0;
    }

	
}
