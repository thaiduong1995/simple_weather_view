package com.thaiduong.test.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thaiduong.test.app_interface.ISentCityInformation;
import com.thaiduong.test.app_interface.ISentCityNameListener;
import com.thaiduong.test.app_interface.ISentCoordinatesListener;
import com.thaiduong.test.fragment.ForecastFragment;
import com.thaiduong.test.fragment.WeatherFragment;

public class ViewPagerAdapter extends FragmentStateAdapter implements ISentCityInformation {
    private final WeatherFragment mWeatherFragment;
    private final ForecastFragment mForecastFragment;

    private String cityName;
    private double lat, lon;
    private boolean isCheck;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        mWeatherFragment = new WeatherFragment();
        mForecastFragment = new ForecastFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ISentCityNameListener mISentCityNameListener;
        if (position == 0) {
            if (isCheck) {
                if (cityName.equals("")) {
                    ((ISentCoordinatesListener) mWeatherFragment).sentCoordinates(lat, lon, true);
                } else {
                    mISentCityNameListener = mWeatherFragment;
                    mISentCityNameListener.sentCityName(cityName, true);
                }
            }
            return mWeatherFragment;
        }
        mISentCityNameListener = mForecastFragment;
        mISentCityNameListener.sentCityName(cityName, true);
        return mForecastFragment;

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public void sentCityInformation(String cityName, double lat, double lon, boolean isCheck) {
        this.cityName = cityName;
        this.lat = lat;
        this.lon = lon;
        this.isCheck = isCheck;
    }
}
