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
import com.example.demo.util.CurrencyExchange;
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
	@Autowired
	private CurrencyExchange ex;

	@Transactional
	public void scrapMarge() {
		// 모두 삭제
		infoMapper.scrapMargeClean();
		
		// 이 둘을 먼저 실행
		for (GameInfo info : getSteamTopInfo()) {
			infoMapper.scrapMarge(info);
		}
		for (GameInfo info : getNintendoExpInfo()) {
			infoMapper.scrapMarge(info);
		}
		
		for (GameInfo info : getSteamDCInfo()) {
			infoMapper.scrapMarge(info);
		}
		for (GameInfo info : getSteamNewInfo()) {
			infoMapper.scrapMarge(info);
		}
		for (GameInfo info : getDirectNewInfo()) {
			infoMapper.scrapMarge(info);
		}
		for (GameInfo info : getNintendoDCInfo()) {
			infoMapper.scrapMarge(info);
		}
		for (GameInfo info : getNintendoNewInfo()) {
			infoMapper.scrapMarge(info);
		}
		for (GameInfo info : getPlanetNewInfo()) {
			infoMapper.scrapMarge(info);
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
			
			String thumb = data.getThumb().replace("capsule_sm_120", "capsule_231x87");
			gi.setGiThumb(thumb);
			
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
			
			String thumb = data.getThumb().replace("capsule_sm_120", "capsule_231x87");
			gi.setGiThumb(thumb);
			
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
	
	private List<GameInfo> getSteamTopInfo() {
		List<ScrapData> steamTop = scraped.getSteamTop();
		List<GameInfo> giList = new ArrayList<>();
		for (ScrapData data : steamTop) {
			GameInfo gi = new GameInfo();
			String nTitle = norm.normalize(data.getTitle());
			// gi.setGiNum() - selectkey
			gi.setGNum(dataMapper.getGNum(nTitle));
			gi.setGiPlatform("steam");
			gi.setGiTitle(data.getTitle());
			
			String thumb = data.getThumb().replace("capsule_sm_120", "capsule_231x87");
			gi.setGiThumb(thumb);
			
			gi.setGiPrice(norm.strToInt(data.getPrice()));
			gi.setGiFprice(norm.strToInt(data.getFprice()));
			
			int dc_rate = norm.strToInt(data.getRate());
			gi.setGiRate(dc_rate);
			
			if(dc_rate > 0)
				gi.setGiState("dc");
			else
				gi.setGiState("top");
			
			gi.setGiLink(data.getLink());
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
			
			if(dc_rate > 0)
				gi.setGiState("newdc");
			else
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
	
	private List<GameInfo> getNintendoExpInfo() {
		List<ScrapData> nintendoExp = scraped.getNintendoExp();
		List<GameInfo> giList = new ArrayList<>();
		for (ScrapData data : nintendoExp) {
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
			
			if(gi.getGiRate() > 0) {
				gi.setGiState("dc");
			} else {
				gi.setGiState("top");
			}
			
			gi.setGiLink(data.getLink());
			gi.setGiDate(data.getScrapedAt());
			giList.add(gi);
		}
		return giList;
	}
	
	private List<GameInfo> getPlanetNewInfo() {
		List<ScrapData> planetNew = scraped.getPlanetNew();
		List<GameInfo> giList = new ArrayList<>();
		double exRate = ex.usdToKrwRate();
		for (ScrapData data : planetNew) {
			GameInfo gi = new GameInfo();
			String nTitle = norm.normalize(data.getTitle());
			// gi.setGiNum() - selectkey
			gi.setGNum(dataMapper.getGNum(nTitle));
			gi.setGiPlatform("planet");
			gi.setGiTitle(data.getTitle());
			gi.setGiThumb(data.getThumb());
			
			int price = (int) Math.round(norm.strToDouble(data.getPrice()) * exRate);
			int fprice = (int) Math.round(norm.strToDouble(data.getFprice()) * exRate);
			gi.setGiPrice(price);
			gi.setGiFprice(fprice);
			
			gi.setGiRate(norm.strToInt(data.getRate()));
			gi.setGiLink(data.getLink());
			
			if(gi.getGiRate() > 0)
				gi.setGiState("newdc");
			else
				gi.setGiState("new");
			
			gi.setGiDate(data.getScrapedAt());
			giList.add(gi);
		}
		return giList;
	}

	public void updateGameInfo() {
		infoMapper.updateGameInfo();
	}

}