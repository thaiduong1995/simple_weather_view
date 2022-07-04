package com.thaiduong.test.app_interface;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thaiduong.test.model.LocationResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ILocationServiceApi {

    Gson gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .create();

    ILocationServiceApi apiServiceLocation = new Retrofit.Builder()
            .baseUrl("https://www.mapquestapi.com/geocoding/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ILocationServiceApi.class);

    @GET("address")
    Call<LocationResult> location(@QueryMap Map<String, String> locationResult);
}
