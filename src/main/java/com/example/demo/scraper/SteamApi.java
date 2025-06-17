package com.example.demo.scraper;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.mapper.GameDataMapper;
import com.example.demo.model.Game;
import com.example.demo.model.SteamAppListResponse;
import com.example.demo.util.Normalize;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SteamApi {
	@Autowired
	private GameDataMapper mapper;
	@Autowired
	private ObjectMapper objMapper;
	@Autowired
	private Normalize title;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// pk 생성
	private List<Long> getSequenceValues(int count) {
		return jdbcTemplate.query("SELECT seq_game.nextval FROM dual CONNECT BY LEVEL <= ?", new Object[] { count },
				(rs, rowNum) -> rs.getLong(1));
	}

	// 모두 가져오기
	public void insertSteamApi() {
		try {
			// 1. JSON 다운로드
			String apiUrl = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
			RestTemplate restTemplate = new RestTemplate();
			String json = restTemplate.getForObject(apiUrl, String.class);

			// 2. 파싱
			SteamAppListResponse response = objMapper.readValue(json, SteamAppListResponse.class);
			List<Game> apps = response.getApplist().getApps();

			// 3. 필터링 + 정규화
			List<Long> existingAppIds = mapper.selectAllAppIds(); // DB에 이미 저장된 appid
			Set<Long> existingAppIdSet = new HashSet<>(existingAppIds); // 중복 steam_appid
			Set<String> seenNTitles = new HashSet<>();	// 중복 n_title

			List<Game> filtered = apps.stream()
			    .filter(app -> app.getSteamAppid() != null) // steam_appid null 필터
			    .filter(app -> !existingAppIdSet.contains(app.getSteamAppid())) // steam_appid 중복 필터
			    .filter(app -> title.isKoreanOrEnglishOnly(app.getGTitle())) // 한/영/숫자 필터
			    .filter(app -> app.getGTitle() != null && !app.getGTitle().isBlank()) // g_title null 필터
			    .filter(app -> app.getGTitle().length() >= 2)
			    .peek(app -> app.setNTitle(title.normalize(app.getGTitle()))) // n_title set
			    .filter(app -> app.getNTitle() != null && !app.getNTitle().isBlank()) // n_title null 필터
			    .filter(app -> seenNTitles.add(app.getNTitle())) // n_title 중복 필터
			    .collect(Collectors.toList());
			

			// 4. pk
			List<Long> seqList = getSequenceValues(filtered.size());
			AtomicInteger index = new AtomicInteger(0);
			filtered.forEach(app -> {
				app.setGNum(seqList.get(index.getAndIncrement()));
			});

			// 5. 500개 단위로 batch insert
			for (int i = 0; i < filtered.size(); i += 500) {
				List<Game> batch = filtered.subList(i, Math.min(i + 500, filtered.size()));
				mapper.insertSteamApi(batch);
				System.out.println(i + "개 삽입");
				Thread.sleep(200);
			}
			System.out.println("✅ Steam App 목록 저장 완료 (" + filtered.size() + "건)");
			System.out.println("💡 기존 게임 수: " + existingAppIdSet.size());
			System.out.println("📦 삽입 대상 수: " + filtered.size());
			
		} catch (IOException e) {
			System.err.println("❌ API 호출 오류: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("❌ 오류: " + e.getMessage());
		}
		
	}
	
	// 최신만 가져오기
	public void margeSteamApi() {
	    try {
	        // 1. JSON 다운로드
	        String apiUrl = "https://api.steampowered.com/ISteamApps/GetAppList/v2/";
	        RestTemplate restTemplate = new RestTemplate();
	        String json = restTemplate.getForObject(apiUrl, String.class);

	        // 2. 파싱
	        SteamAppListResponse response = objMapper.readValue(json, SteamAppListResponse.class);
	        List<Game> apps = response.getApplist().getApps();

	        // 3. 가장 최근 appid 기준 상위 100개만 추출
	        List<Game> recentApps = apps.stream()
	            .filter(app -> app.getSteamAppid() != null && app.getSteamAppid().matches("\\d+"))
	            .sorted(Comparator.comparingLong(app -> Long.parseLong(((Game)app).getSteamAppid())).reversed())
	            .limit(100)
	            .collect(Collectors.toList());

	        // 4. DB에 없는 것만 필터링 + n_title 정규화 + 중복 제거
	        List<Long> existingAppIds = mapper.selectAllAppIds();
	        Set<Long> existingAppIdSet = new HashSet<>(existingAppIds);
	        Set<String> seenNTitles = new HashSet<>();

	        List<Game> filtered = recentApps.stream()
	            .filter(app -> !existingAppIdSet.contains(Long.parseLong(app.getSteamAppid())))
	            .filter(app -> app.getGTitle() != null && !app.getGTitle().isBlank())
	            .filter(app -> title.isKoreanOrEnglishOnly(app.getGTitle()))
	            .filter(app -> app.getGTitle().length() >= 2)
	            .peek(app -> app.setNTitle(title.normalize(app.getGTitle())))
	            .filter(app -> app.getNTitle() != null && !app.getNTitle().isBlank())
	            .filter(app -> seenNTitles.add(app.getNTitle()))
	            .collect(Collectors.toList());

	        // 5. 시퀀스 생성 및 insert
	        if (!filtered.isEmpty()) {
	            List<Long> seqList = getSequenceValues(filtered.size());
	            AtomicInteger index = new AtomicInteger(0);
	            filtered.forEach(app -> app.setGNum(seqList.get(index.getAndIncrement())));
	        }
	        
	        for(Game g : filtered)
	        	mapper.margeSteamApi(g);

	        System.out.println("✅ Steam App 목록 저장 완료 (" + filtered.size() + "건)");
	        System.out.println("💡 기존 게임 수: " + existingAppIdSet.size());
	        System.out.println("📦 삽입 대상 수: " + filtered.size());

	    } catch (IOException e) {
	        System.err.println("❌ API 호출 오류: " + e.getMessage());
	    } catch (Exception e) {
	        System.err.println("❌ 오류: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
}
