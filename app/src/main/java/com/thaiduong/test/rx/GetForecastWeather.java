package com.thaiduong.test.rx;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.thaiduong.test.app_interface.ISetForecastWeatherCities;
import com.thaiduong.test.app_interface.IWeatherForecastServiceApi;
import com.thaiduong.test.key.KeyApi;
import com.thaiduong.test.model.Day;
import com.thaiduong.test.model.WeatherForecast;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetForecastWeather {
    public static final String TAG = GetForecastWeather.class.getName();

    private final ISetForecastWeatherCities mISetForecastWeatherCities;
    private final String cityName;
    private Disposable mDisposable;
    private List<Day> mDayList;

    public GetForecastWeather(ISetForecastWeatherCities mISetForecastWeatherCities, String cityName) {
        this.mISetForecastWeatherCities = mISetForecastWeatherCities;
        this.cityName = cityName;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void letSubscribe() {
        Observable<WeatherForecast> mWeatherForecastObservable = getForecastWeatherObservable();
        Observer<WeatherForecast> mWeatherForecastObserver = getForecastWeatherObserver();
        assert mWeatherForecastObservable != null;
        mWeatherForecastObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mWeatherForecastObserver);
    }

    private Observer<WeatherForecast> getForecastWeatherObserver() {
        return new Observer<WeatherForecast>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull WeatherForecast weatherForecast) {
                mDayList = weatherForecast.getDays();
                Log.d(TAG, "onNext: " + mDayList.size());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                mISetForecastWeatherCities.setForecastWeatherCities(mDayList);
                Log.d(TAG, "onComplete: ");
                if (mDisposable != null) {
                    mDisposable.dispose();
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Observable<WeatherForecast> getForecastWeatherObservable() {
        return IWeatherForecastServiceApi.apiWeatherForecast.weatherForecastCall
                (cityName, KeyApi.KEY_API_FORECAST);
    }

}
