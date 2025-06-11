package com.example.demo.scraper;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ScrapMapper;
import com.example.demo.model.ScrapData;
import com.example.demo.service.Normalize;

@Service
public class SteamDCScraper implements Scraper {
	@Autowired
	private ScrapMapper mapper;
	@Autowired
	private Normalize norm;

	@Override
	public void scrap() {
		try {
			mapper.steamDCClean();
			int pk = 0;
			for (int page = 1; page <= 5; page++) {

				Document doc = Jsoup
						.connect("https://store.steampowered.com/search/?supportedlang=koreana&category1=998&specials=1&ndl=1&page=" + page)
						.userAgent("Mozilla").get();
				Elements games = doc.select(".search_result_row");

				for (Element game : games.stream().limit(24).toList()) {
					ScrapData g = new ScrapData();
					String title = game.select(".title").text();
					pk++;
					g.setPk(pk);
					g.setTitle(title);
					g.setRate(game.select(".discount_pct").text().trim());
					g.setPrice(game.select(".discount_original_price").text().trim());
					g.setFprice(game.select(".discount_final_price").text().trim());
					g.setThumb(game.select("img").attr("src"));
					g.setLink(game.attr("href"));
					g.setScrapedAt(Timestamp.valueOf(LocalDateTime.now()));
					g.setNTitle(norm.normalize(title));
					mapper.steamDCInsert(g);
				}
			}

			System.out.println("✅ Steam 게임 데이터 저장 완료");

		} catch (IOException e) {
			System.err.println("❌ 스크래핑 오류: " + e.getMessage());
		}
	}

	@Override
	public String getName() {
		return "steamdc";
	}
}