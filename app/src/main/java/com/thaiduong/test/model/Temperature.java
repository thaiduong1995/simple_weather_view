package com.thaiduong.test.model;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    private float temp;
    @SerializedName("feels_like")
    private final float feelsLike;
    @SerializedName("temp_min")
    private final float tempMin;
    @SerializedName("temp_max")
    private final float tempMax;
    private int pressure;
    private int humidity;

    public Temperature(float temp, float feelsLike, float tempMin, float tempMax, int pressure, int humidity) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public int tempCovert(float temp) {
        return (int) Math.round(temp - 272.15);
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getFeelsLike() {
        return feelsLike;
    }

    public float getTempMin() {
        return tempMin;
    }

    public float getTempMax() {
        return tempMax;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
