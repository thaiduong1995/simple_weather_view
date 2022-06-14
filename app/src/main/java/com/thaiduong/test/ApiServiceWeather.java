package com.thaiduong.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiServiceWeather {

    Gson gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .create();

    ApiServiceWeather apiServiceWeather = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServiceWeather.class);

    @GET("data/2.5/weather")
    Call<Weather> weather(@Query("lat") double lat,
                          @Query("lon") double lon,
                          @Query("appid") String appid);

}
