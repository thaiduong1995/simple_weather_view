package com.thaiduong.test.model;

import java.util.List;

public class Weather {
    private List<WeatherCurrent> weather;
    private Temperature main;
    private Wind wind;
    private String name;
    private Country sys;

    public Weather(List<WeatherCurrent> weather, Temperature main, Wind wind, String name, Country sys) {
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.name = name;
        this.sys = sys;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getSys() {
        return sys;
    }

    public void setSys(Country sys) {
        this.sys = sys;
    }
}
