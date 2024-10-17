package com.example.app.controller.C02OpenWeatherMapAPI;

import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
@RequestMapping("/openWeather")
public class OpenWeatherMapAPIController {

    //API KEY
    private String serviceKey = "b7a263e63bfe790ff0081e9b619e7c91";

    @GetMapping("/get/{lat}/{lon}")
    public void currentWeather(
                @PathVariable("lat") String lat,
                @PathVariable("lon") String lon
            ){

        //URL
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid="+serviceKey;

        //Request
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Root> response =  rt.exchange(url, HttpMethod.GET,null,Root.class);
        System.out.println(response.getBody());

    }


    //--------------------------------------
    @Data
    private static class Clouds{
        public int all;
    }
    @Data
    private static  class Coord{
        public double lon;
        public double lat;
    }
    @Data
    private static  class Main{
        public int temp;
        public double feels_like;
        public int temp_min;
        public int temp_max;
        public int pressure;
        public int humidity;
    }
    @Data
    private static  class Root{
        public Coord coord;
        public ArrayList<Weather> weather;
        public String base;
        public Main main;
        public int visibility;
        public Wind wind;
        public Clouds clouds;
        public int dt;
        public Sys sys;
        public int timezone;
        public int id;
        public String name;
        public int cod;
    }
    @Data
    private static  class Sys{
        public int type;
        public int id;
        public String country;
        public int sunrise;
        public int sunset;
    }
    @Data
    private static class Weather{
        public int id;
        public String main;
        public String description;
        public String icon;
    }
    @Data
    private static class Wind{
        public double speed;
        public int deg;
    }



}
