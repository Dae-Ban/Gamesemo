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
import com.example.demo.util.KSTTime;
import com.example.demo.util.Normalize;
@Service
public class DirectNewScraper implements Scraper {
	@Autowired
	private ScrapMapper mapper;
	@Autowired
	private Normalize norm;

	@Override
	public void scrap() {
		try {
			mapper.directNewClean();
			int pk = 0;
			for (int page = 1; page <= 5; page++) {

				Document doc = Jsoup
						.connect("https://directg.net/game/game.html?sort=opendate&search_goods_kind=1&page=" + page)
						.userAgent("Mozilla").get();
				Elements games = doc.select(".spacer");

				for (Element game : games.stream().limit(20).toList()) {
					ScrapData g = new ScrapData();
					String title = game.select(".vm-product-descr-container-1 h2").text();
					title = norm.noTag(title);
					pk++;
					g.setPk(pk);
					g.setTitle(title);

					String dcRate = game.select(".vm-product-descr-container-1 span.label-danger").text().trim();
					if (dcRate == null || dcRate.isEmpty())
						g.setRate("0");
					else
						g.setRate(dcRate);

					String fprice = game.select("div.PricesalesPrice > span.PricesalesPrice").text().trim();
					String price = game.select("div.PricebasePrice > span.PricebasePrice").text().trim();

					// 품절인 경우 가격 정보가 없으면 스킵
					if ((fprice == null || fprice.isEmpty()) && (price == null || price.isEmpty())) {
						System.out.println("⛔ 품절 또는 가격 정보 없음: " + title);
						continue;
					}

					if (fprice.equals("무료")) fprice = "0";
					if (price == null || price.isEmpty())
						g.setPrice(fprice);
					else
						g.setPrice(price);

					g.setFprice(fprice);

					g.setThumb(game.select(".vm-product-media-container img").attr("src"));
					g.setLink(game.select(".vm-product-media-container a").attr("href"));
					g.setScrapedAt(KSTTime.nowTimestamp());
					g.setNTitle(norm.normalize(title));

					if(g.getNTitle() == null || g.getNTitle().isEmpty())
						continue;
					else
						mapper.directNewInsert(g);
				}

			}

			System.out.println("✅ 게임 데이터 저장 완료");

		} catch (IOException e) {
			System.err.println("❌ 스크래핑 오류: " + e.getMessage());
		}
	}

	@Override
	public String getName() {
		return "directnew";
	}

}
