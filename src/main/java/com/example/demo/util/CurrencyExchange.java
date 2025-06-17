package com.example.demo.util;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CurrencyExchange {

	private final RestTemplate restTemplate = new RestTemplate();
	private static final String URL = "https://api.exchangerate.host/convert?from=";

	public double usdToKrwRate() {
		Map<String, Object> response = restTemplate.getForObject(URL + "USD&to=KRW", Map.class);
		return (Double) response.get("result");
	}
}