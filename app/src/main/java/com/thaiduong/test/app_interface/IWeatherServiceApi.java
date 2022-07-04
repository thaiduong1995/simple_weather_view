package com.thaiduong.test.app_interface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thaiduong.test.model.Weather;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IWeatherServiceApi {

    Gson gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .create();

    IWeatherServiceApi apiServiceWeather = new Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IWeatherServiceApi.class);

    @GET("data/2.5/weather")
    Call<Weather> weather(@Query("lat") double lat,
                          @Query("lon") double lon,
                          @Query("lang") String lang,
                          @Query("appid") String appid);

}
