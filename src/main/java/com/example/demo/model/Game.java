package com.example.demo.model;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

// 게임 VO
@Data
@Alias("game")
public class Game {
	private long gNum;
	@JsonProperty("name")
	private String gTitle;
	private String nTitle;
	@JsonProperty("appid")
	private String steamAppid;
}
