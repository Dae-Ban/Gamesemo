package com.example.demo.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class KSTTime {

	private static final ZoneId KST_ZONE = ZoneId.of("Asia/Seoul");

	// 현재 KST 기준 LocalDateTime
	public static LocalDateTime nowLocal() {
		return LocalDateTime.now(KST_ZONE);
	}

	// 현재 KST 기준 ZonedDateTime
	public static ZonedDateTime nowZoned() {
		return ZonedDateTime.now(KST_ZONE);
	}

	// 현재 KST 기준 Timestamp
	public static Timestamp nowTimestamp() {
		return Timestamp.from(Instant.now().atZone(KST_ZONE).toInstant());
	}

	// LocalDateTime → KST 기준 Timestamp 변환
	public static Timestamp toKstTimestamp(LocalDateTime ldt) {
		ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault()).withZoneSameInstant(KST_ZONE);
		return Timestamp.from(zdt.toInstant());
	}
}
