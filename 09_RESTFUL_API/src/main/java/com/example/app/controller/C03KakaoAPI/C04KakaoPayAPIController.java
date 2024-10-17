package com.example.app.controller.C03KakaoAPI;

import com.google.gson.JsonObject;
import jdk.jfr.MetadataDefinition;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Controller
@Slf4j
@RequestMapping("/kakao")
public class C04KakaoPayAPIController {

    @GetMapping("/pay/main")
    public void index(){
        log.info("/GET /pay/main...");
    }

    @GetMapping("/pay/req")
    public @ResponseBody KakaoPayResponse pay(){
        log.info("/GET /pay/req...");

        //URL
        String url="https://open-api.kakaopay.com/online/v1/payment/ready";
        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","DEV_SECRET_KEY DEV3A13CDE2F6E7C6C9748D7D9FEE4DD3509651F");
        headers.add("Content-Type","application/json");

        //PARAM
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("cid","TC0ONETIME");
        obj.put("partner_order_id","partner_order_id");
        obj.put("partner_user_id","partner_user_id");
        obj.put("item_name","초코파이");
        obj.put("quantity","1");
        obj.put("total_amount","2200");
        obj.put("tax_free_amount","0");
        obj.put("approval_url","http://localhost:8080/kakao/pay/success");
        obj.put("cancel_url","http://localhost:8080/kakao/pay/cancel");
        obj.put("fail_url","http://localhost:8080/kakao/pay/fail");


        //ENTITY(HEADER + PARAM)
        HttpEntity< JSONObject > entity = new HttpEntity(obj,headers);
        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoPayResponse> response =rt.exchange(url, HttpMethod.POST,entity,KakaoPayResponse.class);
        System.out.println(response.getBody());

        return response.getBody();
    }


    @GetMapping("/pay/success")
    public void success(){
        log.info("GET /kakao/pay/success..");
    }
    @GetMapping("/pay/fail")
    public void fail(){
        log.info("GET /kakao/pay/fail..");
    }
    @GetMapping("/pay/cancel")
    public void cancel(){
        log.info("GET /kakao/pay/cancel..");
    }
    @Data
    private static class KakaoPayResponse{
        public String tid;
        public boolean tms_result;
        public LocalDateTime created_at;
        public String next_redirect_pc_url;
        public String next_redirect_mobile_url;
        public String next_redirect_app_url;
        public String android_app_scheme;
        public String ios_app_scheme;
    }
}
