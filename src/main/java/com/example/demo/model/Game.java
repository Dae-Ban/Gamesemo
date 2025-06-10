package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 게임 VO
@AllArgsConstructor
@Getter
@Alias("game")
public class Game {
	private int gNum;
	private String gTitle;
	private String nTitle;
	private String steamAppid;
}
