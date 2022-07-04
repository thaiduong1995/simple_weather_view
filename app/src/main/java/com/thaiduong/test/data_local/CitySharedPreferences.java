package com.thaiduong.test.data_local;

import android.content.Context;
import android.content.SharedPreferences;

public class CitySharedPreferences {
    private static final String CITY_NAME = "CITY_NAME";
    private final Context mContext;

    public CitySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }

    public void putCityName(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CITY_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getCityName(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(CITY_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
}
