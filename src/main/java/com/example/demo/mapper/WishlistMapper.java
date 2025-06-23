package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.GameInfo;
import com.example.demo.model.Wishlist;

@Mapper
public interface WishlistMapper {

	int addWishlist(Wishlist wishlist);

	int wishlistExists(@Param("gNum") int gnum, @Param("id") String id);
	
    int countWishlist(@Param("id") String id, @Param("keyword") String keyword);
    boolean isGameInWishlist(@Param("id") String id, @Param("gNum") Long gNum);
    int addToWishlist(@Param("id") String id, @Param("gNum") Long gNum);
    int removeFromWishlist(@Param("id") String id, @Param("gNum") Long gNum);

    List<GameInfo> getWishlistPaged(@Param("id") String id,
                                    @Param("order") String order,
                                    @Param("offset") int offset,
                                    @Param("limit") int limit);

    List<GameInfo> searchWishlistPaged(@Param("id") String id,
                                       @Param("keyword") String keyword,
                                       @Param("order") String order,
                                       @Param("offset") int offset,
                                       @Param("limit") int limit); 

}
