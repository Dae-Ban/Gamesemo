package com.example.demo.util;

import org.springframework.stereotype.Component;

@Component
public class Normalize {

	// 한글, 영어 아닌 제목 제외
	public boolean isKoreanOrEnglishOnly(String title) {
		return title.matches("^[a-zA-Z0-9가-힣\\s\\p{Punct}]+$");
	}

	// 제목 정규화
	public String normalize(String title) {
		if (title == null)
			return null;
		return title.toLowerCase()
					.replaceAll("[^a-z0-9가-힣]", "")
					.replaceAll("\\s+", "")
					.trim();
	}
	
	public int strToInt(String str) {
		return Integer.parseInt(str.replaceAll("[^0-9]", ""));
	}
	
	// [태그] 제거
	public String noTag(String rawTitle) {
		return rawTitle.replaceAll("\\[.*?\\]\\s*", "").trim();
	}
}