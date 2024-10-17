package com.example.app.controller.C03KakaoAPI;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/kakao")
public class C01KakaoMapController {

    @GetMapping("/map/01")
    public void map(){
        log.info("GET /kakao/map/01...");
    }
    @GetMapping("/map/02")
    public void map2(){
        log.info("GET /kakao/map/02...");
    }
}
