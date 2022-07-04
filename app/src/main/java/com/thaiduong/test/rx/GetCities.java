package com.thaiduong.test.rx;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thaiduong.test.app_interface.IGetCityList;
import com.thaiduong.test.model.City;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetCities {
    private final IGetCityList mIGetCityList;
    private Context mContext;
    private Disposable mDisposable;
    private List<String> mCities;

    public GetCities(Context mContext, IGetCityList mIGetCityList) {
        this.mContext = mContext;
        this.mIGetCityList = mIGetCityList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void letSubscribe() {
        Observable<City> mCityObservable = getCityObservable();
        Observer<City> mCityObserver = getCityObserver();
        assert mCityObservable != null;
        mCityObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mCityObserver);
    }

    private Observer<City> getCityObserver() {
        return new Observer<City>() {

            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                mDisposable = d;
                mCities = new ArrayList<>();
            }

            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull City city) {
                mCities.add(city.toString());
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                mIGetCityList.getDataSearch(mCities);
                if (mDisposable != null) {
                    mDisposable.dispose();
                    mContext = null;
                }
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Observable<City> getCityObservable() {
        List<City> mCityList = getListCites();
        if (mCityList == null || mCityList.isEmpty()) {
            return null;
        }
        return Observable.create(emitter -> {
            for (City mCity :
                    mCityList) {
                if (!emitter.isDisposed()) {
                    emitter.onNext(mCity);
                }
            }
            if (!emitter.isDisposed()) {
                emitter.onComplete();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<City> getListCites() {
        try {
            InputStream mInputStream = mContext.getAssets().open("cities.json");
            int mFileLength = mInputStream.available();
            byte[] mCityData = new byte[mFileLength];
            mInputStream.read(mCityData);
            mInputStream.close();
            String mCityStr = new String(mCityData, StandardCharsets.US_ASCII);
            Gson mGSon = new Gson();

            Type mType = new TypeToken<List<City>>() {
            }.getType();
            return mGSon.fromJson(mCityStr, mType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
