package com.thaiduong.test.model;

import com.google.gson.annotations.SerializedName;

public class Day {
    private String datetime;
    private String description;
    private float humidity;
    private String icon;
    @SerializedName("tempmax")
    private float tempMax;
    @SerializedName("tempmin")
    private float tempMin;

    public Day(String datetime, String description, float humidity, String icon, float tempMax, float tempMin) {
        this.datetime = datetime;
        this.description = description;
        this.humidity = humidity;
        this.icon = icon;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public String convertUriIconName() {
        StringBuilder result = new StringBuilder();
        String[] strArr = icon.split("-");
        if (strArr.length == 1) {
            result = new StringBuilder(strArr[0]);
        } else {
            for (String str :
                    strArr) {
                result.append(str).append("_");
            }
            result = new StringBuilder(result.substring(0, result.length() - 1));
        }
        return result.toString();
    }

    public String convertTemperatureToDegrees(float temp) {
        return Math.round((temp - 32) / 1.800) + "\u2103";
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getTempMax() {
        return tempMax;
    }

    public void setTempMax(float tempMax) {
        this.tempMax = tempMax;
    }

    public float getTempMin() {
        return tempMin;
    }

    public void setTempMin(float tempMin) {
        this.tempMin = tempMin;
    }
}
