package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.GameDataMapper;
import com.example.demo.mapper.GameInfoMapper;
import com.example.demo.mapper.ScrapMapper;
import com.example.demo.model.GameInfo;
import com.example.demo.model.ScrapData;
import com.example.demo.util.Normalize;

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

	public List<GameInfo> gameInfoList() {
		return infoMapper.gameInfoList();
	}

	@Transactional
	public void insertGameInfo() {
		// 모두 삭제
		infoMapper.gameInfoClean();
		for (GameInfo info : getSteamDCInfo()) {
			infoMapper.insertGameInfo(info);
		}
		for (GameInfo info : getSteamNewInfo()) {
			infoMapper.insertGameInfo(info);
		}
		for (GameInfo info : getDirectNewInfo()) {
			infoMapper.insertGameInfo(info);
		}
		for (GameInfo info : getNintendoDCInfo()) {
			infoMapper.insertGameInfo(info);
		}
		for (GameInfo info : getNintendoNewInfo()) {
			infoMapper.insertGameInfo(info);
		}
	}

	private List<GameInfo> getSteamDCInfo() {
		List<ScrapData> steamDC = scraped.getSteamDC();
		List<GameInfo> giList = new ArrayList<>();
		for (ScrapData data : steamDC) {
			GameInfo gi = new GameInfo();
			String nTitle = norm.normalize(data.getTitle());
			// gi.setGiNum() - selectkey
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

	private List<GameInfo> getSteamNewInfo() {
		List<ScrapData> steamNew = scraped.getSteamNew();
		List<GameInfo> giList = new ArrayList<>();
		for (ScrapData data : steamNew) {
			GameInfo gi = new GameInfo();
			String nTitle = norm.normalize(data.getTitle());
			// gi.setGiNum() - selectkey
			gi.setGNum(dataMapper.getGNum(nTitle));
			gi.setGiPlatform("steam");
			gi.setGiTitle(data.getTitle());
			gi.setGiThumb(data.getThumb());
			gi.setGiPrice(norm.strToInt(data.getPrice()));
			gi.setGiFprice(norm.strToInt(data.getFprice()));
			gi.setGiRate(norm.strToInt(data.getRate()));
			gi.setGiLink(data.getLink());
			gi.setGiState("new");
			gi.setGiDate(data.getScrapedAt());
			giList.add(gi);
		}
		return giList;
	}

	private List<GameInfo> getDirectNewInfo() {
		List<ScrapData> directNew = scraped.getDirectNew();
		List<GameInfo> giList = new ArrayList<>();
		for (ScrapData data : directNew) {
			GameInfo gi = new GameInfo();
			String nTitle = norm.normalize(data.getTitle());
			// gi.setGiNum() - selectkey
			gi.setGNum(dataMapper.getGNum(nTitle));
			gi.setGiPlatform("direct");
			gi.setGiTitle(data.getTitle());
			gi.setGiThumb(data.getThumb());
			gi.setGiPrice(norm.strToInt(data.getPrice()));
			gi.setGiFprice(norm.strToInt(data.getFprice()));

			int dc_rate = norm.strToInt(data.getRate());
			gi.setGiRate(dc_rate);
			
			gi.setGiState("new");
			gi.setGiLink(data.getLink());
			gi.setGiDate(data.getScrapedAt());
			giList.add(gi);
		}
		return giList;
	}

	private List<GameInfo> getNintendoDCInfo() {
		List<ScrapData> nintendoDC = scraped.getNintendoDC();
		List<GameInfo> giList = new ArrayList<>();
		for (ScrapData data : nintendoDC) {
			GameInfo gi = new GameInfo();
			String nTitle = norm.normalize(data.getTitle());
			// gi.setGiNum() - selectkey
			gi.setGNum(dataMapper.getGNum(nTitle));
			gi.setGiPlatform("nintendo");
			gi.setGiTitle(data.getTitle());
			gi.setGiThumb(data.getThumb());
			gi.setGiPrice(norm.strToInt(data.getPrice()));
			gi.setGiFprice(norm.strToInt(data.getFprice()));

			if (gi.getGiPrice() > 0) {
			    double rate = (1 - ((double) gi.getGiFprice() / gi.getGiPrice())) * 100;
			    gi.setGiRate((int) Math.round(rate));
			} else {
			    gi.setGiRate(0);
			    gi.setGiFprice(0);
			}
			
			gi.setGiState("dc");
			gi.setGiLink(data.getLink());
			gi.setGiDate(data.getScrapedAt());
			giList.add(gi);
		}
		return giList;
	}
	
	private List<GameInfo> getNintendoNewInfo() {
		List<ScrapData> nintendoNew = scraped.getNintendoNew();
		List<GameInfo> giList = new ArrayList<>();
		for (ScrapData data : nintendoNew) {
			GameInfo gi = new GameInfo();
			String nTitle = norm.normalize(data.getTitle());
			// gi.setGiNum() - selectkey
			gi.setGNum(dataMapper.getGNum(nTitle));
			gi.setGiPlatform("nintendo");
			gi.setGiTitle(data.getTitle());
			gi.setGiThumb(data.getThumb());
			gi.setGiPrice(norm.strToInt(data.getPrice()));
			gi.setGiFprice(norm.strToInt(data.getFprice()));
			
			if (gi.getGiPrice() > 0) {
				double rate = (1 - ((double) gi.getGiFprice() / gi.getGiPrice())) * 100;
				gi.setGiRate((int) Math.round(rate));
			} else {
				gi.setGiRate(0);
				gi.setGiFprice(0);
			}
			
			
			gi.setGiState("new");
			gi.setGiLink(data.getLink());
			gi.setGiDate(data.getScrapedAt());
			giList.add(gi);
		}
		return giList;
	}
}