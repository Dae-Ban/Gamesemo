package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.model.GameInfo;

@Mapper
public interface WishlistMapper {
    int countWishlist(@Param("id") String id, @Param("keyword") String keyword);
    boolean isGameInWishlist(@Param("id") String id, @Param("giNum") Long giNum);
    int addToWishlist(@Param("id") String id, @Param("giNum") Long giNum);
    int removeFromWishlist(@Param("id") String id, @Param("giNum") Long giNum);

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
