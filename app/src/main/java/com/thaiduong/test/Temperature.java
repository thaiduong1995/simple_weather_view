package com.thaiduong.test;

public class Temperature {
    private float temp;
    private int pressure;
    private int humidity;

    public int tempCovert() {
        return (int) Math.round(temp - 272.15);
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
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

    public Temperature(float temp, int pressure, int humidity) {
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
    }
}
