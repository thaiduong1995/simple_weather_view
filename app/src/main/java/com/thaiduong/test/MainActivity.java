package com.thaiduong.test;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    Button btnSearch;
    TextView tvResult;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strSearch = editSearch.getText().toString().trim();
                if (strSearch.length() != 0) {
                    callApiLocation(strSearch);
                } else {
                    Toast toast;
                    toast = Toast.makeText(MainActivity.this,
                            "Vui lòng nhập tên thành phồ bạn muốn xem thời tiết",
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void callApiLocation(String str) {
        mProgressDialog.show();
        Map<String, String> coordinates = new HashMap();
        coordinates.put("key", KeyApi.KEY_API_LOCATION);
        coordinates.put("location", str);
        ApiServiceLocation.apiServiceLocation.location(coordinates).enqueue(new Callback<LocationResult>() {
            @Override
            public void onResponse(Call<LocationResult> call, Response<LocationResult> response) {
                LocationResult location = response.body();
                if (location != null) {
                    double lat = location.getResults().get(0).getLocations().get(0).getLatLng().getLat();
                    double lon = location.getResults().get(0).getLocations().get(0).getLatLng().getLon();
                    callApiWeather(lat, lon);
                } else {
                    mProgressDialog.dismiss();
                    tvResult.setGravity(Gravity.CENTER);
                    tvResult.setText("Vị trí hành phố bạn muốn xem chưa được cập nhật trên hệ thống\n " +
                            "Rất xin lỗi bạn vì sự bất tiện này");
                }
            }

            @Override
            public void onFailure(Call<LocationResult> call, Throwable t) {
                mProgressDialog.dismiss();
                tvResult.setGravity(Gravity.CENTER);
                tvResult.setText("Không tìm thấy thành phố bạn muốn xem\n " +
                        "Bạn vui lòng kiểm tra lại tên thành phố");
            }
        });
    }

    private void callApiWeather(double lat, double lon) {
        ApiServiceWeather.apiServiceWeather.weather(lat, lon, KeyApi.KEY_API_WEATHER).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();
                mProgressDialog.dismiss();
                if (weather != null) {
                    tvResult.setText(weather.toString());
                } else {
                    tvResult.setGravity(Gravity.CENTER);
                    tvResult.setText("Thời tiết hành phố bạn muốn xem chưa được cập nhật trên hệ thống\n " +
                            "Rất xin lỗi bạn vì sự bất tiện này");
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                mProgressDialog.dismiss();
                tvResult.setText("Thành phố bạn muốn xem chưa được cập nhật lên hệ thống\n" +
                        "Rất xin lỗi bạn vì sự bất tiện này");
            }
        });
    }

    private void initUI() {
        editSearch = (EditText) findViewById(R.id.edit_search);
        btnSearch = (Button) findViewById(R.id.btn_search);
        tvResult = (TextView) findViewById(R.id.tv_result);
        mProgressDialog = new ProgressDialog(MainActivity.this);
        mProgressDialog.setMessage("Vui lòng đợi...");
    }
}