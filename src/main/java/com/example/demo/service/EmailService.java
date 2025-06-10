package com.example.demo.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtmlMessage(String to, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(to);
            helper.setSubject("오늘의 게임 할인 소식🎮");
            helper.setText(htmlContent, true);
            helper.setFrom("2j1william@gmail.com");
            helper.addInline("logoImage", new File("src/main/resources/static/images/icons/logo.png"));
            helper.addInline("emailIcon", new File("src/main/resources/static/images/icons/email.png"));
            helper.addInline("kakaoIcon", new File("src/main/resources/static/images/icons/kakao.png"));
            
            mailSender.send(message);
            System.out.println("이메일 전송 완료: " + to);
        } catch (Exception e) {
            System.out.println("이메일 전송 실패: " + to);
            e.printStackTrace();
        }
    }
}