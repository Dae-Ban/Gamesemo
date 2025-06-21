package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Wishlist {

	private int wishNum; // 번호 (PK)
	private int gNum; // 게임 번호
	private String id; // 사용자 아이디
}
