package com.thaiduong.test.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.thaiduong.test.R;
import com.thaiduong.test.adapter.ViewPagerAdapter;
import com.thaiduong.test.app_interface.ISentCityInformation;
import com.thaiduong.test.app_interface.ISentCityNameListener;
import com.thaiduong.test.data_local.DataLocalManager;
import com.thaiduong.test.key.KeyApi;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ISentCityNameListener {
    private ViewPagerAdapter mViewPagerAdapter;

    private ViewPager2 mViewPager;
    private TextView tvSearch;
    private TabLayout mTabLayout;
    private ImageView ivLocation;

    private long backPressedTime;
    private Toast mToast;

    private String cityName;
    private double lat, lon;
    private ISentCityInformation mISentCityInformation;

    private final ActivityResultLauncher<Intent> mIntentActivityResultLauncher = registerForActivityResult
            (new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent intent = result.getData();
                    assert intent != null;
                    cityName = intent.getStringExtra(KeyApi.CITY_NAME_SEARCH);
                    tvSearch.setText(cityName);
                    mISentCityInformation.sentCityInformation(cityName, lat, lon, true);
                    mViewPager.setAdapter(mViewPagerAdapter);
                }
            });

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        if (!DataLocalManager.getCityName().equals("")) {
            cityName = DataLocalManager.getCityName();
            if (cityName.equals("Xom Pho")) {
                tvSearch.setText("Hanoi");
            } else {
                tvSearch.setText(cityName);
            }
        }

        tvSearch.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            mIntentActivityResultLauncher.launch(intent);
        });

        ivLocation.setOnClickListener(v -> getLocationCurrent());

        mViewPagerAdapter = new ViewPagerAdapter(this);
        mISentCityInformation = mViewPagerAdapter;
        mISentCityInformation.sentCityInformation(cityName, lat, lon, false);
        mViewPager.setAdapter(mViewPagerAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Hôm nay");
            } else {
                tab.setText("15 ngày");
            }
        }).attach();

    }

    private void initUI() {
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        tvSearch = findViewById(R.id.tv_search);
        ivLocation = findViewById(R.id.iv_location);
        cityName = "";
        lat = 361;
        lon = 361;
    }

    private void requestLocationPermission() {
        PermissionListener mPermissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Truy cập vị trí thành công"
                        , Toast.LENGTH_SHORT).show();
                getLocationCurrent();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Không được quyền truy cập vị trí\n"
                        + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.create()
                .setPermissionListener(mPermissionListener)
                .setDeniedMessage(
                        "Không thể xác định được vị trí hiện tại của bạn\n" +
                                "\nVui lòng vào phần [Setting] để cho phép truy cập vị trí của bạn")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();
    }

    private void getLocationCurrent() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient mFusedLocationProviderClient
                    = LocationServices.getFusedLocationProviderClient(MainActivity.this);
            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                cityName = "";
                lat = location.getLatitude();
                lon = location.getLongitude();
                mISentCityInformation.sentCityInformation(cityName, lat, lon, true);
                mViewPager.setAdapter(mViewPagerAdapter);
            });
        } else {
            requestLocationPermission();
        }
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            mToast.cancel();
            super.onBackPressed();
            return;
        } else {
            mToast = Toast.makeText(MainActivity.this,
                    "Vui lòng nhấn liên tiếp 2 lần để thoát khỏi ứng dụng",
                    Toast.LENGTH_LONG);
            mToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public void sentCityName(String cityName, boolean isCheck) {
        if (isCheck) {
            this.cityName = cityName;
            tvSearch.setText(this.cityName);
            mISentCityInformation.sentCityInformation(this.cityName, lat, lon, true);
        }
    }
}