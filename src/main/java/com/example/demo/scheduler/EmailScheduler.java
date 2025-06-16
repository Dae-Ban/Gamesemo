package com.example.demo.scheduler;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.mapper.GameInfoMapper;
import com.example.demo.mapper.MemberMapper;
import com.example.demo.model.GameInfo;
import com.example.demo.model.Member;
import com.example.demo.service.EmailService;

@Component
public class EmailScheduler {
	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private GameInfoMapper gameInfoMapper;

//	@Autowired
//	private EmailService emailService;
////	    @Scheduled(fixedDelay = 10000)
//	    @Scheduled(cron = "0 0 10 * * ?") // 매일 오전 10시
//	    public void sendGameSaleEmails() {
//	    	 List<Member> subscribers = memberMapper.selectAllEmailSubscribers();
////	    	    List<GameInfo> nintendoGames = gameInfoMapper.selectTop10GamesByPlatform("nintendo");
////	    	    List<GameInfo> xboxGames = gameInfoMapper.selectTop10GamesByPlatform("xbox");
////	    	    List<GameInfo> ps5Games = gameInfoMapper.selectTop10GamesByPlatform("ps5");
//	    	    List<GameInfo> steamGames = gameInfoMapper.selectTop10GamesByPlatform("steam");
//
////	    	    String htmlContent = buildHtmlContent(steamGames,nintendoGames,xboxGames,ps5Games);
//	    	    String htmlContent = buildHtmlContent(steamGames);
//
//	    	    for (Member member : subscribers) {
//	    	        emailService.sendHtmlMessage("2j1william@gmail.com", htmlContent); //테스트용 
////	    	        emailService.sendHtmlMessage(member.getEmailId()+"@"+member.getEmailDomain(), htmlContent);
//	    	    }
//	    }
//	    
////	    	private String buildHtmlContent(List<GameInfo> steamGames,List<GameInfo> nintendoGames,List<GameInfo> xboxGames,List<GameInfo> ps5Games) {
//	    private String buildHtmlContent(List<GameInfo> steamGames) {
//	        StringBuilder sb = new StringBuilder();
//	        
//	        
//	        sb.append("""
//	            <!DOCTYPE html>
//	            <html lang="ko">
//	            <head>
//	              <meta charset="UTF-8" />
//	              <title>겜세일모아 할인 소식</title>
//	            </head>
//	            <body style="font-family: Arial, sans-serif; background-color: #f0f0f0; padding: 20px; margin: 0;">
//	              <div style="max-width: 900px; margin: 0 auto; background-color: #ffffff; padding: 30px; text-align: center;">
//	                <div style="margin-bottom: 30px;">
//	                  <table style="margin: 0 auto; text-align: center">
//	                    <tr>
//	                      <td><img src="cid:logoImage" alt="logo" style="width: 60px; height: auto; margin-right: 10px;" /></td>
//	                      <td>
//	                        <div style="font-weight: bold; font-size: 24px;">Gamesemo</div>
//	                        <div style="font-size: 14px; color: #666; font-weight:bold">겜 세일 모아</div>
//	                      </td>
//	                    </tr>
//	                  </table>
//	                </div>
//
//	                <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed;">
//	                  <tr>
//	        """);
//
//	  
//	        sb.append(buildPlatformColumn("STEAM", steamGames));
//	        sb.append(buildPlatformColumn("Nintendo", steamGames));
//	        sb.append(buildPlatformColumn("Xbox", steamGames));
//	        sb.append(buildPlatformColumn("PS5", steamGames));
////	        sb.append(buildPlatformColumn("Nintendo", nintendoGames));
////	        sb.append(buildPlatformColumn("Xbox", xboxGames));
////	        sb.append(buildPlatformColumn("PS5", ps5Games));
//	        
//
//	        sb.append("""
//	                  </tr>
//	                </table>
//	              </div>
//	              """);
//	        
//	        sb.append("<div style='margin-top: 30px; font-size: 12px; color: #888; border-top: 1px solid #ccc; padding-top: 10px;'>")
//	        .append("<table width='100%' cellpadding='0' cellspacing='0'>")
//	        .append("<tr>")
//
//	        // 왼쪽 카피라이트
//	        .append("<td align='left' style='padding: 5px;'>")
//	        .append("© 2025 겜세일모아. All rights reserved.")
//	        .append("</td>")
//
//	        // 오른쪽 아이콘들
//	        .append("<td align='right' style='padding: 5px;'>")
//	        .append("<a href='mailto:contact@gamesemo.com?subject=문의드립니다.'>")
//	        .append("<img src='cid:emailIcon' alt='email' style='height: 15px; width: 15px; margin-left: 8px;' /></a>")
//	        .append("<a href='https://open.kakao.com/o/s5khbGAh'>")
//	        .append("<img src='cid:kakaoIcon' alt='kakao' style='height: 15px; width: 15px; margin-left: 8px;' /></a>")
//	        .append("</td>")
//
//	        .append("</tr>")
//	        .append("</table>")
//	        .append("</div>")
//	        .append(" </body>")
//	        .append("</html>");  
//	        
//	        return sb.toString();
//	    }
//
//	    private String buildPlatformColumn(String platformName, List<GameInfo> games) {
//	        StringBuilder sb = new StringBuilder();
//	        sb.append("<td valign='top' style='width: 33.3%; padding: 10px;'>");
//	        sb.append("<div style='background-color: #e6e6e6; padding: 15px;'>");
//	        sb.append("<h2 style='font-size: 16px;'>").append(platformName).append("</h2>");
//	        
//	        DecimalFormat formatter = new DecimalFormat("#,###");
//
//	        for (GameInfo game : games) {
//	            sb.append("<div style='background-color: #fff; padding: 10px; margin-bottom: 15px; text-align: left; overflow: hidden;'>")
//	              .append("<a href='").append(game.getGiLink()).append("' style='text-decoration: none; color: #000;'>")
//	              .append("<img src='").append(game.getGiThumb()).append("' alt='' style='width: 100%; border: none;' />")
//	              .append("<div style='font-weight: bold; font-size: 13px; margin: 8px 0 4px;'>")
//	              .append(game.getGiTitle()).append("</div>")
//	              .append("</a>")
//	              .append("<table style='float: right; font-size: 12px; border-collapse: collapse;'>")
//	              .append("<tr>")
//	              .append("<td style='text-align: center;'><del style='color: #888; text-align: left;'>₩")
//	              .append(formatter.format(game.getGiPrice())).append("</del></td>")
//	              .append("<td rowspan='2'><div style='background-color: #000; color: #fff; border-radius: 12px; padding: 5px 7px; font-size: 15px; margin-left: 5px;'>")
//	              .append(game.getGiRate()).append("%</div></td>")
//	              .append("</tr>")
//	              .append("<tr><td style='font-weight: bold; text-align: center; text-align: left;'>₩")
//	              .append(formatter.format(game.getGiFprice())).append("</td></tr>")
//	              .append("</table>")
//	              .append("</div>");
//	        }
//
//	        sb.append("</div></td>");
//	        
//	        return sb.toString();
//	    }
}
