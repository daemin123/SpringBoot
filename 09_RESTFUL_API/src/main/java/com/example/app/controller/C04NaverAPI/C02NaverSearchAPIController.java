package com.example.app.controller.C04NaverAPI;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Controller
@Slf4j
@RequestMapping("/naver")
public class C02NaverSearchAPIController {

    private String NAVER_CLIENT_ID="z6yn5IQQPYzpAAIoJvS5";

    private String NAVER_CLIENT_SECRET="JyMQDaCyiN";

    @GetMapping("/search/main")
    public void main(){
        log.info("GET /naver/search/main...");
    }
    //도서 검색
    @GetMapping("/search/{keyword}")
    public @ResponseBody SearchBookResponse search(@PathVariable("keyword") String keyword){
        log.info("GET /naver/search/keyword ..." + keyword);

        //URL
        String url="https://openapi.naver.com/v1/search/book.json?query="+keyword;
        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id",NAVER_CLIENT_ID);
        headers.add("X-Naver-Client-Secret",NAVER_CLIENT_SECRET);
        //PARAM
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

        //ENTITY
        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<SearchBookResponse> response = rt.exchange(url, HttpMethod.GET,entity,SearchBookResponse.class);

        //RESPONSE
        System.out.println(response.getBody());
        return response.getBody();
    }

    @Data
    private static class Item{
        public String title;
        public String link;
        public String image;
        public String author;
        public String discount;
        public String publisher;
        public String pubdate;
        public String isbn;
        public String description;
    }
    @Data
    private static class SearchBookResponse{
        public String lastBuildDate;
        public int total;
        public int start;
        public int display;
        public ArrayList<Item> items;
    }


}
