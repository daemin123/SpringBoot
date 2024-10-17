package com.example.app.controller.C03KakaoAPI;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Controller
@Slf4j
@RequestMapping("/kakao/karlo")
public class C05KakaoKarloAPIController {


    private String RESTAPI_KEY="87166502a8aeaf54e9067d26a41e2b67";

    @GetMapping("/index")
    public void index(){
        log.info("GET /kakao/karlo/index");
    }

    @GetMapping("/req/{prompt}")
    public @ResponseBody KarloResponse req(@PathVariable("prompt") String prompt){


        log.info("GET /kakao/karlo/req..." + prompt);

        //URL
        String url="https://api.kakaobrain.com/v2/inference/karlo/t2i";

        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","KakaoAK "+RESTAPI_KEY);
        headers.add("Content-Type","application/json");

        //PARAM
        JSONObject params = new JSONObject();
        params.put("version","v2.1");
        params.put("prompt",prompt);
        params.put("width","1024");
        params.put("height","1024");

        //ENTITY
        HttpEntity<JSONObject> entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KarloResponse> response = rt.exchange(url, HttpMethod.POST,entity,KarloResponse.class);
        //RESPONSE
        System.out.println(response.getBody());

        return response.getBody();

    }

    @Data
    private static class Image{
        public String id;
        public String image;
        public long seed;
        public Object nsfw_content_detected;
        public Object nsfw_score;
    }
    @Data
    private static class KarloResponse{
        public String id;
        public String model_version;
        public ArrayList<Image> images;
    }

}
