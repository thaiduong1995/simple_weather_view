package com.thaiduong.test.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thaiduong.test.R;
import com.thaiduong.test.activities.MainActivity;
import com.thaiduong.test.adapter.WeatherForecastAdapter;
import com.thaiduong.test.app_interface.ISentCityNameListener;
import com.thaiduong.test.app_interface.ISetForecastWeatherCities;
import com.thaiduong.test.model.Day;
import com.thaiduong.test.rx.GetForecastWeather;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment implements ISetForecastWeatherCities, ISentCityNameListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private MainActivity mMainActivity;
    private View mView;
    public ForecastFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ForecastFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForecastFragment newInstance(String param1, String param2) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Rename and change types of parameters
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_forecast, container, false);
        mMainActivity = (MainActivity) getActivity();

        return mView;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void setForecastWeatherCities(List<Day> dayList) {
        if (dayList.size() != 0) {
            RecyclerView rcvWeatherForecast = mView.findViewById(R.id.rcv_weather_forecast);

            LinearLayoutManager layoutManager = new LinearLayoutManager(mMainActivity);
            rcvWeatherForecast.setLayoutManager(layoutManager);
            assert mMainActivity != null;
            DividerItemDecoration decoration = new DividerItemDecoration(mMainActivity, DividerItemDecoration.VERTICAL);
            rcvWeatherForecast.addItemDecoration(decoration);

            WeatherForecastAdapter mWeatherForecastAdapter = new WeatherForecastAdapter(mMainActivity, dayList);
            rcvWeatherForecast.setAdapter(mWeatherForecastAdapter);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void sentCityName(String cityName, boolean isCheck) {
        if (isCheck) {
            GetForecastWeather getForecastWeather = new GetForecastWeather(this, cityName);
            getForecastWeather.letSubscribe();
        }
    }
}