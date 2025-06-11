package com.example.demo.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.controller.CommunityController;
import com.example.demo.mapper.ScrapMapper;
import com.example.demo.model.ScrapData;

@Service
public class SteamNewScraper implements Scraper {

	@Autowired
	private ScrapMapper mapper;
	@Autowired
	private Normalize norm;

	@Override
	public void scrap() {
		try {
			mapper.steamNewClean();
			int pk = 0;
			for (int page = 1; page <= 5; page++) {

				Document doc = Jsoup
						.connect("https://store.steampowered.com/search/?sort_by=Released_DESC&supportedlang=koreana&category1=998&os=win&ndl=1&page=" + page)
						.userAgent("Mozilla").get();
				Elements games = doc.select(".search_result_row");

				for (Element game : games.stream().limit(24).toList()) {
					ScrapData g = new ScrapData();
					String title = game.select(".title").text();
					
					pk++;
					g.setPk(pk);
					
					g.setTitle(title);
					
					String dcRate = game.select(".discount_pct").text().trim();
					if(dcRate == null || dcRate == "")
						g.setRate("0");
					else
						g.setRate(dcRate);
					
					String fprice = game.select(".discount_final_price").text().trim();
					if(fprice.equals("Free"))
						fprice = "0";
					String price = game.select(".discount_original_price").text().trim();
					if(price == null ||price == "")
						g.setPrice(fprice);
					else
						g.setPrice(price);
					g.setFprice(fprice);
					
					g.setThumb(game.select("img").attr("src"));
					g.setLink(game.attr("href"));
					g.setScrapedAt(Timestamp.valueOf(LocalDateTime.now()));
					g.setNTitle(norm.normalize(title));
					mapper.steamNewInsert(g);
				}
			}

			System.out.println("✅ Steam 게임 데이터 저장 완료");

		} catch (IOException e) {
			System.err.println("❌ 스크래핑 오류: " + e.getMessage());
		}
	}

	@Override
	public String getName() {
		return "steamnew";
	}
}