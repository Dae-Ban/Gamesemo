package com.example.demo.util;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class YouTubeUtil {
	@Value("${youtube.api.key}")
	private String apiKey;

	private static final String YOUTUBE_SEARCH_URL = "https://www.googleapis.com/youtube/v3/search";
	private final RestTemplate rest = new RestTemplate();
	private final ObjectMapper mapper = new ObjectMapper();

	public List<YouTubeVideo> search(
	        String keyword,
	        int maxResults,
	        String order,
	        String regionCode,
	        String relevanceLanguage
	) {
	    UriComponentsBuilder b = UriComponentsBuilder
	        .fromHttpUrl(YOUTUBE_SEARCH_URL)
	        .queryParam("part", "snippet")
	        .queryParam("q", keyword)                     // ① URLEncoder 제거
	        .queryParam("type", "video")
	        .queryParam("maxResults", maxResults)
	        .queryParam("key", apiKey);

	    if (order != null)           b.queryParam("order", order);
	    if (regionCode != null)      b.queryParam("regionCode", regionCode);
	    if (relevanceLanguage != null) b.queryParam("relevanceLanguage", relevanceLanguage);

	    URI uri = b.build().encode().toUri();            // ② 한 번만 인코딩
	    JsonNode root = rest.getForObject(uri, JsonNode.class);

	    List<YouTubeVideo> results = new ArrayList<>();
	    if (root != null && root.has("items")) {
	        for (JsonNode item : root.get("items")) {
	            String vid   = item.path("id").path("videoId").asText();
	            JsonNode sn  = item.path("snippet");
	            String title = sn.path("title").asText();
	            String thumb = sn.path("thumbnails").path("medium").path("url").asText();
	            results.add(new YouTubeVideo(vid, title, thumb));
	        }
	    }
	    return results;
	}
}
