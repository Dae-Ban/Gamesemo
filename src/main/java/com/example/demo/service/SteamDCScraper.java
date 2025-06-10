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

import com.example.demo.mapper.ScrapMapper;
import com.example.demo.model.GameInfo;

@Service
public class SteamDCScraper {
	@Autowired
	private ScrapMapper mapper;

	public void steamScrap() {
		try {
			mapper.steamClean();
			for (int page = 1; page <= 5; page++) {

				Document doc = Jsoup
						.connect("https://store.steampowered.com/search/?supportedlang=koreana&specials=1&ndl=" + page)
						.userAgent("Mozilla").get();
				Elements games = doc.select(".search_result_row");

				for (Element game : games.stream().limit(24).toList()) {
					GameInfo g = new GameInfo();
					g.setGiTitle(game.select(".title").text());
					String dcRate = game.select(".discount_pct").text().replaceAll("[^0-9]", "").trim();
					if (dcRate == null) {
						g.setGiRate(0);
					} else {
						g.setGiRate(Integer.parseInt(dcRate));
					}
					g.setGiPrice(Integer
							.parseInt(game.select(".discount_original_price").text().replaceAll("[^0-9]", "").trim()));
					g.setGiFprice(Integer
							.parseInt(game.select(".discount_final_price").text().replaceAll("[^0-9]", "").trim()));
					g.setGiThumb(game.select("img").attr("src"));
					g.setGiLink(game.attr("href"));
					g.setGiDate(Timestamp.valueOf(LocalDateTime.now()));
					mapper.steamInsert(g);
					System.out.println(g.getGiTitle());
					System.out.println(g.getGiRate());
					System.out.println(g.getGiPrice());
					System.out.println(g.getGiFprice());
					System.out.println(g.getGiThumb());
					System.out.println(g.getGiLink());
					System.out.println(g.getGiDate());
				}
			}

			System.out.println("✅ Steam 게임 데이터 저장 완료");

		} catch (IOException e) {
			System.err.println("❌ 스크래핑 오류: " + e.getMessage());
		}
	}
}
