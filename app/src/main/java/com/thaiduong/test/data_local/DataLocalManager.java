package com.thaiduong.test.data_local;

import android.content.Context;

public class DataLocalManager {
    private static final String PREF_CITY_NAME = "PREF_CITY_NAME";
    private static DataLocalManager instance;
    private CitySharedPreferences citySharedPreferences;

    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.citySharedPreferences = new CitySharedPreferences(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static String getCityName() {
        return DataLocalManager.getInstance().citySharedPreferences.getCityName(PREF_CITY_NAME);
    }

    public static void setCityName(String cityName) {
        DataLocalManager.getInstance().citySharedPreferences.putCityName(PREF_CITY_NAME, cityName);
    }
}
