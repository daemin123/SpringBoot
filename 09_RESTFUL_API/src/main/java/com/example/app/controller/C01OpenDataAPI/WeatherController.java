package com.example.app.controller.C01OpenDataAPI;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/openData")
public class WeatherController {

    private String serviceKey="xYZ80mMcU8S57mCCY/q8sRsk7o7G8NtnfnK7mVEuVxdtozrl0skuhvNf34epviHrru/jiRQ41FokE9H4lK0Hhg==";



    @GetMapping(value = "/weather/{nx}/{ny}/{basedate}/{basetime}",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Item> weather(
            @PathVariable("nx")int nx,
            @PathVariable("ny")int ny,
            @PathVariable("basedate") String basedate,
            @PathVariable("basetime") String basetime
            )
    {
        log.info("/openData/weather.." + nx + " , " + ny+", " );
        String url="http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?";
        url += "serviceKey="+serviceKey;
        url += "&numOfRows="+100;
        url += "&pageNo="+1;
        url += "&dataType=json";
        url += "&base_date="+basedate;
        url += "&base_time="+basetime;
        url += "&nx="+nx;
        url += "&ny="+ny;
        //HEADER x
        //PARAM x
        //ENTITY x

        //REQUEST
        RestTemplate rt = new RestTemplate();
        ResponseEntity<WeatherResponse> response = rt.exchange(url, HttpMethod.GET,null,WeatherResponse.class);
        System.out.println(response.getBody());
        response.getBody().getResponse().getBody().getItems().getItem().forEach(item->{
            System.out.println(item);
        });

        return response.getBody().getResponse().getBody().getItems().getItem();
    }

    //------------------------
    @Data
    private static class Body{
        public String dataType;
        public Items items;
        public int pageNo;
        public int numOfRows;
        public int totalCount;
    }
    @Data
    private static class Header{
        public String resultCode;
        public String resultMsg;
    }
    @Data
    private static class Item{
        public String baseDate;
        public String baseTime;
        public String category;
        public int nx;
        public int ny;
        public String obsrValue;
    }
    @Data
    private static class Items{
        public ArrayList<Item> item;
    }
    @Data
    private static class Response{
        public Header header;
        public Body body;
    }
    @Data
    private static class WeatherResponse{
        public Response response;
    }
}


