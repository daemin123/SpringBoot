package com.example.app.controller.C04NaverAPI;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@Slf4j
@RequestMapping("/naver")
public class C01NaverLoginAPIController {

    private String NAVER_CLIENT_ID="z6yn5IQQPYzpAAIoJvS5";
    private String NAVER_CLIENT_SECRET="JyMQDaCyiN";
    private String REDIRECT_URL="http://localhost:8080/naver/getCode";

    private NaverTokenResponse naverTokenResponse;

    @GetMapping("/login")
    public void login(){
        log.info("GET /naver/login....");
    }
    @PostMapping("/login")
    public String login_post(){
        return "redirect:https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id="+NAVER_CLIENT_ID+"&state=STATE_STRING&redirect_uri="+REDIRECT_URL;
    }
    @GetMapping("/getCode")
    public  String getCode(@RequestParam("code")String code, @RequestParam("state")String state)
    {
        log.info("GET /naver/getCode..code : " + code + " state : " + state);

        //URL
        String url="https://nid.naver.com/oauth2.0/token";
        //HEADER
        HttpHeaders headers = new HttpHeaders();
        //PARAM
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",NAVER_CLIENT_ID);
        params.add("client_secret",NAVER_CLIENT_SECRET);
        params.add("code",code);
        params.add("state",state);

        //ENTITY
        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<NaverTokenResponse> response = rt.exchange(url, HttpMethod.POST,entity,NaverTokenResponse.class);

        //RESPONSE
        System.out.println(response.getBody());
        this.naverTokenResponse = response.getBody();

        return "redirect:/naver/main";

    }

    @GetMapping("/main")
    public void main(Model model){
        log.info("GET /naver/main...");
        //URL
        String url="https://openapi.naver.com/v1/nid/me";
        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+naverTokenResponse.getAccess_token());

        //PARAM
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

        //ENTITY
        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<NaverProfileResponse> response = rt.exchange(url, HttpMethod.POST,entity,NaverProfileResponse.class);

        //RESPONSE
        System.out.println(response.getBody());
        model.addAttribute("profile",response.getBody());

    }


    @GetMapping("/logout")
    public @ResponseBody void logout(){
        log.info("GET /naver/logout....");

        //URL
        String url="https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id="+NAVER_CLIENT_ID+"&client_secret="+NAVER_CLIENT_SECRET+"&access_token="+naverTokenResponse.getAccess_token();
        //HEADER
        HttpHeaders headers = new HttpHeaders();
        //PARAM
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

        //ENTITY
        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(url, HttpMethod.GET,null,String.class);

        //RESPONSE
        System.out.println(response.getBody());

    }

    @GetMapping("/disconnect")
    public String disConnect(){
        return "redirect:https://nid.naver.com/nidlogin.logout?returl=https://www.naver.com/";
    }


    //----------------------
    @Data
    private static class NaverTokenResponse{
        public String access_token;
        public String refresh_token;
        public String token_type;
        public String expires_in;
    }

    //------------------------------------
    @Data
    private static class Response{
        public String id;
        public String nickname;
        public String profile_image;
        public String email;
        public String name;
    }
    @Data
    private static class NaverProfileResponse{
        public String resultcode;
        public String message;
        public Response response;
    }


}
