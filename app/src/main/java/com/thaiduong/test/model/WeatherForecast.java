package com.thaiduong.test.model;

import java.util.List;

public class WeatherForecast {
    private List<Day> days;

    public WeatherForecast() {
    }

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }
}
