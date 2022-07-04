package com.thaiduong.test.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.thaiduong.test.R;
import com.thaiduong.test.activities.MainActivity;
import com.thaiduong.test.app_interface.ILocationServiceApi;
import com.thaiduong.test.app_interface.ISentCityNameListener;
import com.thaiduong.test.app_interface.ISentCoordinatesListener;
import com.thaiduong.test.app_interface.IWeatherServiceApi;
import com.thaiduong.test.data_local.DataLocalManager;
import com.thaiduong.test.database.CityDatabase;
import com.thaiduong.test.key.KeyApi;
import com.thaiduong.test.model.City;
import com.thaiduong.test.model.LocationResult;
import com.thaiduong.test.model.Weather;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment implements ISentCityNameListener, ISentCoordinatesListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View mView;
    private MainActivity mMainActivity;
    private LinearLayout llResult;
    private TextView tvDate, tvTemp, tvFeelsLike, tvDescription, tvWindSpeed, tvWindDirection, tvMaxTemp, tvMinTemp, tvHumidity, tvPressure, tvTimeSunRise, tvTimeSunSet;
    private ImageView ivDescription;
    private ProgressDialog mProgressDialog;
    private String cityName;
    private double lat, lon;
    private boolean isCheckCityName, isCheckCoordinates;
    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mMainActivity = (MainActivity) getActivity();
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
        mView = inflater.inflate(R.layout.fragment_weather, container, false);

        initUI();
        //Test first install
        if (!isCheckCoordinates && !isCheckCityName) {
            if (!DataLocalManager.getCityName().equals("")) {
                callApiLocation(DataLocalManager.getCityName());
            } else {
                llResult.setVisibility(View.INVISIBLE);
            }
        }
        //Get weather by city name
        if (isCheckCityName) {
            callApiLocation(cityName);
        }
        //Get weather by coordinates
        if (isCheckCoordinates) {
            callApiWeather(lat, lon);
        }

        return mView;
    }

    private void callApiLocation(String str) {
        mProgressDialog.show();
        Map<String, String> coordinates = new HashMap<>();
        coordinates.put("key", KeyApi.KEY_API_LOCATION);
        coordinates.put("location", str);
        ILocationServiceApi.apiServiceLocation.location(coordinates).enqueue(new Callback<LocationResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<LocationResult> call, @NonNull Response<LocationResult> response) {
                LocationResult location = response.body();
                if (location != null) {
                    double lat = location.getResults().get(0).getLocations().get(0).getLatLng().getLat();
                    double lon = location.getResults().get(0).getLocations().get(0).getLatLng().getLon();
                    callApiWeather(lat, lon);
                } else {
                    mProgressDialog.dismiss();
                    notifyNotFound("Vị trí hành phố bạn muốn xem chưa được cập nhật trên hệ thống\n" +
                            "Rất xin lỗi bạn vì sự bất tiện này");
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Call<LocationResult> call, @NonNull Throwable t) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void notifyNotFound(String s) {
        new AlertDialog.Builder(mMainActivity)
                .setMessage(s)
                .setPositiveButton("Đồng ý", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void callApiWeather(double lat, double lon) {
        IWeatherServiceApi.apiServiceWeather.weather(lat, lon, "vi", KeyApi.KEY_API_WEATHER)
                .enqueue(new Callback<Weather>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {
                        Weather weather = response.body();
                        mProgressDialog.dismiss();
                        if (weather != null) {
                            if (llResult.getVisibility() == View.INVISIBLE) {
                                llResult.setVisibility(View.VISIBLE);
                            }
                            getWeather(weather);
                        } else {
                            notifyNotFound("Thời tiết hành phố bạn muốn xem chưa được cập nhật trên hệ thống\n"
                                    + "Rất xin lỗi bạn vì sự bất tiện này");
                        }
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                        mProgressDialog.dismiss();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private void getWeather(Weather weather) {
        //Create city from location current
        City mCity = new City(weather.getName(), weather.getSys().getCountry());
        //Time
        getTimeCurrent();
        //Temperature
        tvTemp.setText(weather.getMain().tempCovert(weather.getMain().getTemp()) + "\u2103");
        //Temperature feels like
        tvFeelsLike.setText("Cảm thấy như " + weather.getMain().tempCovert
                (weather.getMain().getFeelsLike()) + "\u2103");
        //Image Weather
        Glide.with(this)
                .load(KeyApi.IMAGE_URI + weather.getWeather().get(0).getIcon() + KeyApi.IMAGE_FORMAT)
                .placeholder(R.drawable.ic_load_img_weather)
                .error(R.drawable.ic_load_error)
                .into(ivDescription);
        //Description Weather
        tvDescription.setText(weather.getWeather().get(0).getDescription());
        //Wind
        tvWindSpeed.setText("Tốc độ gió: " + weather.getWind().getSpeed() + " m/s");
        tvWindDirection.setText(weather.getWind().direction());
        //Temperature
        tvMaxTemp.setText("Nhiệt độ cao nhất: " + weather.getMain().tempCovert(weather.getMain().getTempMax()) + "\u2103");
        tvMinTemp.setText("Nhiệt độ thấp nhất: " + weather.getMain().tempCovert(weather.getMain().getTempMin()) + "\u2103");
        //Humidity
        tvHumidity.setText("Độ ẩm: " + weather.getMain().getHumidity() + "%");
        //Pressure
        tvPressure.setText("Áp xuất: " + weather.getMain().getPressure() + " hPa");
        //Set sunrise sunset
        String sunrise = weather.getSys().convertSunriseToTime();
        tvTimeSunRise.setText(sunrise);
        String sunset = weather.getSys().convertSunsetToTime();
        tvTimeSunSet.setText(sunset);
        //Add city to database
        if (!isExist(mCity)) {
            CityDatabase.getInstance(mMainActivity).cityDAO().insertCity(mCity);
        }
        //Save city name to data local
        DataLocalManager.setCityName(weather.getName());

        if (isCheckCoordinates) {
            //gửi cityName lên tvSearch của main activity
            mMainActivity.sentCityName(weather.getName(), true);
            //gửi cityName sang forecast weather
        }

        isCheckCityName = false;
        isCheckCoordinates = false;
    }

    private boolean isExist(City mCity) {
        List<City> mCityListDatabase = CityDatabase.getInstance(mMainActivity).cityDAO().getListCity();
        if (mCityListDatabase == null || mCityListDatabase.size() == 0) {
            return false;
        }
        for (City city :
                mCityListDatabase) {
            if (mCity.compare(city)) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getTimeCurrent() {
        LocalDate mLocalDate = LocalDate.now();
        LocalTime mLocalTime = LocalTime.now();
        tvDate.setText(mLocalTime.format(DateTimeFormatter.ofPattern("HH:mm")) + ", ngày "
                + mLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    private void initUI() {
        llResult = mView.findViewById(R.id.llResult);
        tvDate = mView.findViewById(R.id.tv_date);
        tvTemp = mView.findViewById(R.id.tv_temp);
        tvFeelsLike = mView.findViewById(R.id.tv_feels_like);
        tvDescription = mView.findViewById(R.id.tv_description);
        tvWindSpeed = mView.findViewById(R.id.tv_wind_speed);
        tvWindDirection = mView.findViewById(R.id.tv_wind_direction);
        tvMaxTemp = mView.findViewById(R.id.tv_max_temp);
        tvMinTemp = mView.findViewById(R.id.tv_min_temp);
        tvHumidity = mView.findViewById(R.id.tv_humidity);
        tvPressure = mView.findViewById(R.id.tv_pressure);
        tvTimeSunRise = mView.findViewById(R.id.tv_time_sunrise);
        tvTimeSunSet = mView.findViewById(R.id.tv_time_sunset);
        ivDescription = mView.findViewById(R.id.iv_description);
        mProgressDialog = new ProgressDialog(mMainActivity);
        mProgressDialog.setMessage("Vui lòng đợi...");
    }

    @Override
    public void sentCityName(String cityName, boolean isCheck) {
        if (isCheck) {
            this.cityName = cityName;
        }
        isCheckCityName = isCheck;
    }

    @Override
    public void sentCoordinates(double lat, double lon, boolean isCheck) {
        if (isCheck) {
            this.lat = lat;
            this.lon = lon;
        }
        isCheckCoordinates = isCheck;
    }
}