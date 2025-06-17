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
import com.example.demo.controller.CommunityController;
import com.example.demo.mapper.ScrapMapper;
import com.example.demo.model.ScrapData;
import com.example.demo.util.KSTTime;
import com.example.demo.util.Normalize;

@Service
public class SteamTopScraper implements Scraper {

	@Autowired
	private ScrapMapper mapper;
	@Autowired
	private Normalize norm;

	@Override
	public void scrap() {
		try {
			mapper.steamTopClean();
			int pk = 0;
			for (int page = 1; page <= 6; page++) {

				Document doc = Jsoup
						.connect("https://store.steampowered.com/search/?category1=998&supportedlang=koreana&filter=topsellers&ndl=1&page=" + page)
						.userAgent("Mozilla").get();
				Elements games = doc.select(".search_result_row");

				for (Element game : games.stream().limit(25).toList()) {
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
					
					// 품절인 경우 가격 정보가 없으면 스킵
					if ((fprice == null || fprice.isEmpty()) && (price == null || price.isEmpty())) {
						System.out.println("⛔ 품절 또는 가격 정보 없음: " + title);
						continue;
					}
					
					g.setThumb(game.select("img").attr("src"));
					g.setLink(game.attr("href"));
					g.setScrapedAt(KSTTime.nowTimestamp());
					g.setNTitle(norm.normalize(title));
					mapper.steamTopInsert(g);
				}
			}

			System.out.println("✅ Steam 게임 데이터 저장 완료");

		} catch (IOException e) {
			System.err.println("❌ 스크래핑 오류: " + e.getMessage());
		}
	}

	@Override
	public String getName() {
		return "steamtop";
	}
}