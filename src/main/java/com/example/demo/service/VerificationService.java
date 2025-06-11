package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class VerificationService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	 public void sendVerificationEmail(String to, String token) {
	        String subject = "[Gamesamo]이메일 회원인증 요청";
	        String url = "http://localhost/member/verify?token=" + token;

	        String content = ""
	                + "<html>"
	                + "<body style='font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 30px;'>"
	                + "<table style='margin: 0 auto; text-align: center'>"
	                + "<tr>"
	                + "<td><img src='cid:logoImage' alt='logo' style='width: 60px; height: auto; margin-right: 10px;'></td>"
	                + "<td>"
	                + "<div style='font-weight: bold; font-size: 24px;'>Gamesemo</div>"
	                + "<div style='font-size: 14px; color: #666; font-weight:bold'>겜 세일 모아</div>"
	                + "</td>"
	                + "</tr>"
	                + "</table>"
	                + "<br>"
	                + "<div style='max-width: 500px; margin: auto; background-color: #fff; border-radius: 12px; padding: 30px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>"
	                + "<h2 style='text-align: center; color: #333;'>이메일 인증</h2>"
	                + "<p style='text-align: center;'>아래 버튼을 클릭하면 인증이 완료됩니다.</p>"
	                + "<div style='text-align: center; margin-top: 30px;'>"
	                + "<a href='" + url + "' style='background-color: #2273e6; color: white; padding: 12px 24px; border-radius: 6px; text-decoration: none;'>이메일 인증</a>"
	                + "</div>"
	                + "</div>"
	                + "</body>"
	                + "</html>";
	        
	        MimeMessage message = mailSender.createMimeMessage();

	        try {
	            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
	            helper.setTo(to);
	            helper.setSubject(subject);
	            helper.setText(content, true);
	            mailSender.send(message);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	
}
