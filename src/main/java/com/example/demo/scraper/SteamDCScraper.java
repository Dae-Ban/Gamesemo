package com.example.demo.scraper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ScrapMapper;
import com.example.demo.model.ScrapData;
import com.example.demo.util.KSTTime;
import com.example.demo.util.Normalize;

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
					
					String price = game.select(".discount_original_price").text().trim();
					String fprice = game.select(".discount_final_price").text().trim();
					// 가격 정보가 없으면 스킵
					if ((fprice == null || fprice.isEmpty()) && (price == null || price.isEmpty())) {
						System.out.println("⛔ 품절 또는 가격 정보 없음: " + title);
						continue;
					}
					if (price == null || price.isEmpty()) price = fprice;
					if (fprice == null || fprice.isEmpty()) fprice = price;
					
					g.setPrice(price);
					g.setFprice(fprice);
					
					g.setThumb(game.select("img").attr("src"));
					g.setLink(game.attr("href"));
					g.setScrapedAt(KSTTime.nowTimestamp());
					g.setNTitle(norm.normalize(title));
					
					if(g.getNTitle() == null || g.getNTitle().isEmpty())
						continue;
					else
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