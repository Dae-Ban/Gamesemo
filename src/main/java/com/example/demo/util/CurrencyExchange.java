package com.example.demo.util;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyExchange {

	private static final RestTemplate restTemplate = new RestTemplate();
	private static final String URL = "https://open.er-api.com/v6/latest/USD";
	private static final double DEFAULT_USD_KRW = 1350.0;

	public double usdToKrwRate() {
		try {
			Map<String, Object> response = restTemplate.getForObject(URL, Map.class);
			Map<String, Object> rates = (Map<String, Object>) response.get("rates");

			if (rates != null && rates.get("KRW") instanceof Number) {
				return ((Number) rates.get("KRW")).doubleValue();
			} else {
				System.err.println("환율 정보 없음. 기본값 사용");
				return DEFAULT_USD_KRW;
			}
		} catch (Exception e) {
			System.err.println("환율 API 실패: " + e.getMessage());
			return DEFAULT_USD_KRW;
		}
	}
}
