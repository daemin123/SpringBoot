package com.example.app;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootTest
public class MailSenderTests {

    @Test
    public void t1(){
        //메일 설정
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("jwg135790@gmail.com");
        mailSender.setPassword("cvyc vvar kgvj ecrb");

        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(mailProps);
        
        //메시지 지정
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("jwg8910@naver.com");
        message.setSubject("메일테스트");
        message.setText("내용내용내용");

        //메일발송
        mailSender.send(message);
    }
}
