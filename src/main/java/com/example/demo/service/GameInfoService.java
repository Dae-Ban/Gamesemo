package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.GameDataMapper;
import com.example.demo.mapper.GameInfoMapper;
import com.example.demo.mapper.ScrapMapper;
import com.example.demo.model.GameInfo;
import com.example.demo.model.ScrapData;

@Service
public class GameInfoService {
	@Autowired
	private GameInfoMapper infoMapper;
	@Autowired
	private GameDataMapper dataMapper;
	@Autowired
	private ScrapMapper scraped;
	@Autowired
	private Normalize norm;

	public void insertGameInfo() {

		for(GameInfo info : getSteamDCInfo()) {
			infoMapper.insertGameInfo(info);
		}
	}

	private List<GameInfo> getSteamDCInfo() {
		List<ScrapData> steamDC = scraped.getSteamDC();
		List<GameInfo> giList = new ArrayList<>();
		for (ScrapData data : steamDC) {
			GameInfo gi = new GameInfo();
			String nTitle = norm.normalize(data.getTitle());
			//gi.setGiNum() - selectkey 
			gi.setGNum(dataMapper.getGNum(nTitle));
			gi.setGiPlatform("steam");
			gi.setGiTitle(data.getTitle());
			gi.setGiThumb(data.getThumb());
			gi.setGiPrice(norm.strToInt(data.getPrice()));
			gi.setGiFprice(norm.strToInt(data.getFprice()));
			gi.setGiRate(norm.strToInt(data.getRate()));
			gi.setGiLink(data.getLink());
			gi.setGiState("dc");
			gi.setGiDate(data.getScrapedAt());
			giList.add(gi);
		}
		return giList;
	}
}
