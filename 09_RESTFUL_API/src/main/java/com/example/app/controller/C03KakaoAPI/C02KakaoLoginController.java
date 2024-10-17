package com.example.app.controller.C03KakaoAPI;


import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@Slf4j
@RequestMapping("/kakao")
public class C02KakaoLoginController {

    private String CLIENT_ID="87166502a8aeaf54e9067d26a41e2b67";
    private String REDIRECT_URI="http://localhost:8080/kakao/callback";
    private String LOGOUT_REDIRECT_URI="http://localhost:8080/kakao/main";

    private KakaoResponse kakaoResponse;

    @GetMapping("/getCode")
    public String getCode(){
        log.info("GET /kakao/getCode...");
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code";
    }
    @GetMapping("/callback")
    public String callBack(@RequestParam("code") String code){
        log.info("GET /kakao/code..." + code);

        //URL
        String url = "https://kauth.kakao.com/oauth/token";

        //REQUEST HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //REQUEST PARAM
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id",CLIENT_ID);
        params.add("redirect_uri",REDIRECT_URI);
        params.add("code",code);

        //ENTITY(HEADER + PARAM)
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        //KakaoResponse response = rt.postForObject(url,entity,KakaoResponse.class);
        ResponseEntity<KakaoResponse> response= rt.exchange(url, HttpMethod.POST,entity,KakaoResponse.class);

        System.out.println(response);
        this.kakaoResponse = response.getBody();

        return "redirect:/kakao/main";
    }



    @GetMapping("/main")
    public void main(){
        log.info("GET /kakao/main....");
    }

    @GetMapping(value="/profile",produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody KakaoProfile getProfile(){
        log.info("GET /kakao/profile..");

        // URL
        String url="https://kapi.kakao.com/v2/user/me";

        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        //PARAM(x)

        //ENTITY
        HttpEntity entity = new HttpEntity(headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<KakaoProfile> response = rt.exchange(url,HttpMethod.POST,entity,KakaoProfile.class);
        System.out.println(response.getBody());


        return response.getBody();

    }

    //로그아웃(토큰 만료)
    @GetMapping("/logout")
    public void logout(){
        log.info("GET /kakao/logout...");

        //URL
        String url="https://kapi.kakao.com/v1/user/logout";
        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());
        //PARAM
        //ENTITY
        HttpEntity entity = new HttpEntity(headers);
        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(url,HttpMethod.POST,entity,String.class);
        System.out.println(response.getBody());
    }

    @GetMapping("/logoutWithKakao")
    public @ResponseBody void logoutWithKakao(HttpServletResponse response) throws IOException {
        //
        log.info("GET /kakao/logoutWithKakao");
        response.sendRedirect("https://kauth.kakao.com/oauth/logout?client_id="+CLIENT_ID+"&logout_redirect_uri="+LOGOUT_REDIRECT_URI);
    }

    //연결끊기(카카오 서버와의)
    @GetMapping("/unlink")
    public void unlink(){
        log.info("GET /kakao/unlink...");

        //URL
        String url="https://kapi.kakao.com/v1/user/unlink";
        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());
        //PARAM
        //ENTITY
        HttpEntity entity = new HttpEntity(headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(url,HttpMethod.POST,entity,String.class);
        System.out.println(response.getBody());
    }



    //메세지보내기(나에게)
    @GetMapping("/getCodeMsg")
    public String getCodeMsg(){
        log.info("GET /kakao/getCodeMsg...");
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id="+CLIENT_ID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=talk_message";
    }
    @GetMapping("/message/me/{message}")
    public @ResponseBody void sendMessageMe(@PathVariable("message") String message){
        log.info("GET /kakao/message/me...message : " + message);

        //URL
        String url="https://kapi.kakao.com/v2/api/talk/memo/default/send";

        //HEADER
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+kakaoResponse.getAccess_token());

        //PARAM
        JSONObject template_object = new JSONObject();
        template_object.put("object_type","text");
        template_object.put("text",message);
        template_object.put("link",new JSONObject());
        template_object.put("button_title","버튼제목");

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("template_object",template_object.toString());

        //ENTITY
        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params,headers);

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(url,HttpMethod.POST,entity,String.class);
        System.out.println(response.getBody());
    }

    //-----------------------------------
    //KAKAO ACCESS TOKEN 보관용 RESPONSE
    //-----------------------------------
    @Data
    private static class KakaoResponse{
        public String access_token;
        public String token_type;
        public String refresh_token;
        public int expires_in;
        public String scope;
        public int refresh_token_expires_in;
    }

    //-----------------------------------
    //KAKAO PROFILE 확인용 RESPONSE
    //-----------------------------------
    @Data
    private static class KakaoAccount{
        public boolean profile_nickname_needs_agreement;
        public boolean profile_image_needs_agreement;
        public Profile profile;
        public boolean has_email;
        public boolean email_needs_agreement;
        public boolean is_email_valid;
        public boolean is_email_verified;
        public String email;
    }
    @Data
    private static  class Profile{
        public String nickname;
        public String thumbnail_image_url;
        public String profile_image_url;
        public boolean is_default_image;
        public boolean is_default_nickname;
    }
    @Data
    private static  class Properties{
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }
    @Data
    private static  class KakaoProfile{
        public long id;
        public LocalDateTime connected_at;
        public Properties properties;
        public KakaoAccount kakao_account;
    }


}
