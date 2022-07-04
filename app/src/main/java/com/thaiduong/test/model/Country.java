package com.thaiduong.test.model;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class Country {
    private String country;
    private double sunrise;
    private double sunset;

    public Country(String country, double sunrise, double sunset) {
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String convertSunriseToTime() {
        Instant instant = Instant.ofEpochSecond((long) sunrise);
        return new SimpleDateFormat("HH:mm").format(Date.from(instant));
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String convertSunsetToTime() {
        Instant instant = Instant.ofEpochSecond((long) sunset);
        return new SimpleDateFormat("HH:mm").format(Date.from(instant));
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getSunrise() {
        return sunrise;
    }

    public void setSunrise(double sunrise) {
        this.sunrise = sunrise;
    }

    public double getSunset() {
        return sunset;
    }

    public void setSunset(double sunset) {
        this.sunset = sunset;
    }
}
