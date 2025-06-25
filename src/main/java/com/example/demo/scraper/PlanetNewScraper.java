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
public class PlanetNewScraper implements Scraper {
	@Autowired
	private ScrapMapper mapper;
	@Autowired
	private Normalize norm;

	@Override
	public void scrap() {
		try {
			mapper.planetNewClean();
			int pk = 0;
			for (int page = 1; page <= 7; page++) {
				
				Document doc = Jsoup
						.connect("https://us.gamesplanet.com/search?av=rel&t=game&page=" + page)
						.userAgent("Mozilla").get();
				Elements games = doc.select("div.game_list");

				for (Element game : games.stream().limit(15).toList()) {
					ScrapData g = new ScrapData();
					String title = game.select("h4").text();
					pk++;
					g.setPk(pk);
					g.setTitle(title);
					
					String price = game.select(".prices .price_base").text().trim();
					String fprice = game.select(".prices .price_current").text().trim();
					if(fprice == null || fprice.isEmpty())
						fprice = game.select(".price-final_price .price").text().trim();
					// 가격 정보가 없으면 스킵
					if (fprice == null || fprice.isEmpty()) {
						System.out.println("⛔ 품절 또는 가격 정보 없음: " + title);
						continue;
					}
					if (price == null || price.isEmpty()) price = fprice;
					if (fprice == null || fprice.isEmpty()) fprice = price;
					g.setPrice(price);
					g.setFprice(fprice);
					
					String dcRate = game.select(".prices .price_saving").text().trim();
					g.setRate((dcRate == null || dcRate.isEmpty()) ? "0" : dcRate);
					
					g.setThumb(game.select("a.stretched-link > img").attr("src"));
					g.setLink("https://us.gamesplanet.com" + game.select("h4 > a.stretched-link").attr("href"));
					g.setScrapedAt(KSTTime.nowTimestamp());
					g.setNTitle(norm.normalize(title));
					
					if(g.getNTitle() == null || g.getNTitle().isEmpty())
						continue;
					else
						mapper.planetNewInsert(g);
				}
			}

			System.out.println("✅ 게임 데이터 저장 완료");

		} catch (IOException e) {
			System.err.println("❌ 스크래핑 오류: " + e.getMessage());
		}
	}

	@Override
	public String getName() {
		return "planetnew";
	}

}
