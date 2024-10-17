package com.example.app.controller.C05GoogleAPI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequestMapping("/google")
public class C01GoogleMailAPIController {

    @GetMapping("/mail/main")
    public void main(){
        log.info("GET /google/mail/main....");
    }

    @Autowired
    JavaMailSender javaMailSender;

    @GetMapping("/mail/req")
    public String req(@RequestParam("email") String email, @RequestParam("text") String text){
        log.info("GET /google/mail/req....email : " + email + " text : "+ text);
        log.info("javaMailSender : " + javaMailSender);

        //메시지 지정
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[메일테스트]");
        message.setText(text);

        javaMailSender.send(message);

        return "redirect:/google/mail/main?message=Success";
    }
}
