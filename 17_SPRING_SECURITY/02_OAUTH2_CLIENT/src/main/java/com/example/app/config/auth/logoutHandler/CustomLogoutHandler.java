package com.example.app.config.auth.logoutHandler;

import com.example.app.config.auth.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class CustomLogoutHandler implements LogoutHandler {

    private String NAVER_CLIENT_ID="z6yn5IQQPYzpAAIoJvS5";
    private String NAVER_CLIENT_SECRET="JyMQDaCyiN";

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("CustomLogoutHandler's logout()...");
        HttpSession session = request.getSession(false);
        if(session!=null)
            session.invalidate();

        //OAUTH2 Client Logout Request

        //Kakao Logout
        PrincipalDetails principalDetails= (PrincipalDetails)authentication.getPrincipal();
        String accessToken = principalDetails.getAccessToken();
        String provider =  principalDetails.getUserDto().getProvider(); //kakao,google,naver

        System.out.println("PROVIDER : " + provider + " ACCESSTOKEN : " + accessToken);


        if(provider!=null && provider.startsWith("kakao")){
            //1)URL
            String url="https://kapi.kakao.com/v1/user/logout";
            //2)HEADER
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization","Bearer "+accessToken);
            //3)PARAMS
            MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

            //4)ENTITY
            HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params,headers);

            //5)REQUEST
            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> resp =
            rt.exchange(url, HttpMethod.POST,entity,String.class);

        }else if(provider!=null && provider.startsWith("naver")){
            //URL
            String url="https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id="+NAVER_CLIENT_ID+"&client_secret="+NAVER_CLIENT_SECRET+"&access_token="+accessToken;
            //HEADER
            HttpHeaders headers = new HttpHeaders();
            //PARAM
            MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

            //ENTITY
            HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params,headers);

            //REQUEST
            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> resp = rt.exchange(url, HttpMethod.GET,null,String.class);

            //RESPONSE
            System.out.println(resp.getBody());
        }else if(provider!=null && provider.startsWith("google")){
            //URL
            String url="https://accounts.google.com/o/oauth2/revoke?token="+accessToken;
            //HEADER
            HttpHeaders headers = new HttpHeaders();
            //PARAM
            MultiValueMap<String,String> params = new LinkedMultiValueMap<>();

            //ENTITY
            HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params,headers);

            //REQUEST
            RestTemplate rt = new RestTemplate();
            ResponseEntity<String> resp = rt.exchange(url, HttpMethod.GET,null,String.class);

            //RESPONSE
            System.out.println(resp.getBody());
        }


        //6)RESPONSE



        //Naver Logout

//        //1)URL
//        String url="";
//        //2)HEADER
//        HttpHeaders headers = new HttpHeaders();
//        //3)PARAMS
//        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
//
//        //4)ENTITY
//        HttpEntity< MultiValueMap<String,String> > entity = new HttpEntity(params,headers);
//
//        //5)REQUEST
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> resp =
//                rt.exchange(url, HttpMethod.GET,entity,String.class);

        //Google Logout


    }
}
