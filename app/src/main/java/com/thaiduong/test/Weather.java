package com.thaiduong.test;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.List;

public class Weather {
    private List<WeatherCurrent> weather;
    private Temperature main;
    private Wind wind;

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public String toString() {
        String result = "";
        result += String.format("Thời tiết: %s%n", weather.get(0).getDescription());
        result += String.format("Nhiệt độ: %d\u2103%n", main.tempCovert());
        result += String.format("Áp suất: %d hPa%n", main.getPressure());
        result += String.format("Độ ẩm: %d%%%n", main.getHumidity());
        result += String.format("Tốc độ gió: %.2f m/s%n", wind.getSpeed());
        return result;
    }

    public Weather(List<WeatherCurrent> weather, Temperature main, Wind wind) {
        this.weather = weather;
        this.main = main;
        this.wind = wind;
    }

    public List<WeatherCurrent> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherCurrent> weather) {
        this.weather = weather;
    }

    public Temperature getMain() {
        return main;
    }

    public void setMain(Temperature main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}
