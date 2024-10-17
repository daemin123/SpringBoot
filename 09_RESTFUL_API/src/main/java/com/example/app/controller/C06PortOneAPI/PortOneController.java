package com.example.app.controller.C06PortOneAPI;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/portOne")
public class PortOneController {

    //인증결제 연동하기
    //DOCUMENT : https://developers.portone.io/docs/ko/authpay/guide?v=v1

    //SDK 놀이터 : https://sdk-playground.portone.io/
    //PG사코드 정보 : https://developers.portone.io/docs/ko/tip/pg-2?v=v1

    // 관리자 페이지(결제내역확인 : https://classic-admin.portone.io/

    private PortOneTokenResponse portOneTokenResponse;

    @GetMapping("/main")
    public void main(){
        log.info("GET /portOne/main...");
    }

    //엑세스토큰 발급받기
    @GetMapping("/getToken")
    public @ResponseBody  void getToken(){
        log.info("GET /portOne/getToken..");

        //URL
        String url="https://api.iamport.kr/users/getToken";
        //HEADER
        HttpHeaders headers = new HttpHeaders();

        //PARAMS
        MultiValueMap params = new LinkedMultiValueMap();
        params.add("imp_key","1546549255738924");
        params.add("imp_secret","Zjy29fdoI6cNNwIZYMrDX4dkLCLvf6HFyFbbVCNwlRD5YzHCEQV4onWbydWFVbT1ID1Zw0Kp6POYsvKg");

        //ENTITY
        HttpEntity<  MultiValueMap<String, String> > entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneTokenResponse> response = rt.exchange(url,HttpMethod.POST,entity,PortOneTokenResponse.class);
        //RESPONSE
        System.out.println(response.getBody());
        this.portOneTokenResponse = response.getBody();

    }

    //인증된 사용자 정보 가져오기
    @GetMapping(value="/getAuthInfo/{imp_uid}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PortOneAuthInfoResponse getAuthInfo(@PathVariable("imp_uid") String imp_uid ){
        getToken();
        log.info("GET /portOne/getAuthInfo.."+imp_uid);

        //URL
        String url="https://api.iamport.kr/certifications/"+imp_uid;
        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        headers.add("Authorization","Bearer "+portOneTokenResponse.getResponse().getAccess_token());
        //PARAMS
        MultiValueMap params = new LinkedMultiValueMap();

        //ENTITY
        HttpEntity<  MultiValueMap<String, String> > entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<PortOneAuthInfoResponse> response = rt.exchange(url,HttpMethod.GET,entity,PortOneAuthInfoResponse.class);
        //RESPONSE
        System.out.println(response.getBody());

        return response.getBody();

    }


    //결제 정보 확인

    //결제 취소 요청
    @GetMapping("/cancel/{imp_uid}")
    public @ResponseBody void cancel(
            @PathVariable String imp_uid,
            @PathVariable String pay_id
    ){

        getToken();
        log.info("GET /payment/cancel..");
        // access-token 받기


        //URL
        String url = "https://api.iamport.kr/payments/cancel";

        //Request Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+portOneTokenResponse.getResponse().getAccess_token());
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        //Request Body
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("imp_uid",imp_uid);

        //Hader+Body
        HttpEntity< MultiValueMap<String,String>> entity = new HttpEntity(params,headers);

        //요청
        RestTemplate restTemplate = new RestTemplate();

        //반환값확인
        ResponseEntity<String> resp =  restTemplate.exchange(url, HttpMethod.POST,entity,String.class);

        System.out.println(resp);
        System.out.println(resp.getBody());
    }



    //---------------------------
    // AccessToken 발급 Class
    //---------------------------
    @Data
    private static class TokenResponse{
        public String access_token;
        public int now;
        public int expired_at;
    }
    @Data
    private static  class PortOneTokenResponse{
        public int code;
        public Object message;
        public TokenResponse response;
    }

    //-----------------------------
    //인증정보 가져오기 Class
    //-----------------------------
    @Data
    private static class AuthInfoResponse{
        public int birth;
        public String birthday;
        public boolean certified;
        public int certified_at;
        public boolean foreigner;
        public Object foreigner_v2;
        public Object gender;
        public String imp_uid;
        public String merchant_uid;
        public String name;
        public String origin;
        public String pg_provider;
        public String pg_tid;
        public String phone;
        public Object unique_in_site;
        public String unique_key;
    }
    @Data
    private static class PortOneAuthInfoResponse{
        public int code;
        public Object message;
        public AuthInfoResponse response;
    }
}
