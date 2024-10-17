package com.example.app.controller.C05GoogleAPI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/google/cal")
public class C02GoogleCalendarAPIController {

    @GetMapping("/main")
    public void main(){
        log.info("GET /google/cal/main...");
    }
}
