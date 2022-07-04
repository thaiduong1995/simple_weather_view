package com.thaiduong.test.app_interface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thaiduong.test.model.WeatherForecast;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IWeatherForecastServiceApi {

    Gson gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .create();

    IWeatherForecastServiceApi apiWeatherForecast = new Retrofit.Builder()
            .baseUrl("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(IWeatherForecastServiceApi.class);

    @GET("timeline/{cityName}")
    Observable<WeatherForecast> weatherForecastCall(@Path("cityName") String cityName, @Query("key") String key);
}
