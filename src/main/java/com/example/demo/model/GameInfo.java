package com.example.demo.model;

import java.sql.Timestamp;
import org.apache.ibatis.type.Alias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Alias("gameinfo")
@Data
public class GameInfo {
    private int giNum;           // PK
	  private int gNum;            // 게임 고유 번호
    private String giPlatform;   // 플랫폼 (예: steam, nintendo)
    private String giTitle;      // 게임 제목
    private String giThumb;      // 썸네일 이미지 URL
    
    
    private int giPrice;         // 원래 가격
    private int giFprice;        // 할인된 가격
    private int giRate;          // 할인율
    private String giLink;       // 게임 링크 URL
    private String giState;      // 상태 (예: 활성, 비활성)
    private Timestamp giDate;    // 등록일
    private String steamAppid;


//	public int getGiNum() {
//		return giNum;
//	}
//	public void setGiNum(int giNum) {
//		this.giNum = giNum;
//	}
//	public int getgNum() {
//		return gNum;
//	}
//	public void setgNum(int gNum) {
//		this.gNum = gNum;
//	}
//	public String getGiPlatform() {
//		return giPlatform;
//	}
//	public void setGiPlatform(String giPlatform) {
//		this.giPlatform = giPlatform;
//	}
//	public String getGiTitle() {
//		return giTitle;
//	}
//	public void setGiTitle(String giTitle) {
//		this.giTitle = giTitle;
//	}
//	public String getGiThumb() {
//		return giThumb;
//	}
//	public void setGiThumb(String giThumb) {
//		this.giThumb = giThumb;
//	}
//	public int getGiPrice() {
//		return giPrice;
//	}
//	public void setGiPrice(int giPrice) {
//		this.giPrice = giPrice;
//	}
//	public int getGiFprice() {
//		return giFprice;
//	}
//	public void setGiFprice(int giFprice) {
//		this.giFprice = giFprice;
//	}
//	public int getGiRate() {
//		return giRate;
//	}
//	public void setGiRate(int giRate) {
//		this.giRate = giRate;
//	}
//	public String getGiLink() {
//		return giLink;
//	}
//	public void setGiLink(String giLink) {
//		this.giLink = giLink;
//	}
//	public String getGiState() {
//		return giState;
//	}
//	public void setGiState(String giState) {
//		this.giState = giState;
//	}
//	public Timestamp getGiDate() {
//		return giDate;
//	}
//	public void setGiDate(Timestamp giDate) {
//		this.giDate = giDate;
//	}
//	public String getSteamAppid() {
//		return steamAppid;
//	}
//	public void setSteamAppid(String steamAppid) {
//		this.steamAppid = steamAppid;
//	}
//	public String getGNum() {
//		return gNum+"";
//	}

}