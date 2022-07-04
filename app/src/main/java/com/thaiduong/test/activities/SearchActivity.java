package com.thaiduong.test.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thaiduong.test.R;
import com.thaiduong.test.adapter.CityAdapter;
import com.thaiduong.test.app_interface.IGetCityList;
import com.thaiduong.test.app_interface.ISentCityNameListener;
import com.thaiduong.test.database.CityDatabase;
import com.thaiduong.test.key.KeyApi;
import com.thaiduong.test.model.City;
import com.thaiduong.test.rx.GetCities;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements ISentCityNameListener, IGetCityList {
    public static final String TAG = SearchActivity.class.getName();
    private ImageView ivBack, ivDelete;
    private AutoCompleteTextView editSearch;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        GetCities mGetCities = new GetCities(SearchActivity.this, this);
        mGetCities.letSubscribe();

        initUI();

        ivBack.setOnClickListener(v -> finish());

        ivDelete.setOnClickListener(v -> editSearch.getText().clear());

        Log.d(TAG, "onCreate: ");
    }

    private void initUI() {
        Log.d(TAG, "initUI: ");
        ivBack = findViewById(R.id.iv_back);
        ivDelete = findViewById(R.id.iv_delete);
        editSearch = findViewById(R.id.edit_search);

        ivDelete.setVisibility(View.GONE);

        List<City> mCityList = CityDatabase.getInstance(this).cityDAO().getListCity();
        if (mCityList != null && mCityList.size() > 0) {
            RecyclerView rcvCity = findViewById(R.id.rcv_city);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            rcvCity.setLayoutManager(layoutManager);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this
                    , DividerItemDecoration.VERTICAL);
            rcvCity.addItemDecoration(itemDecoration);
            CityAdapter mCityAdapter = new CityAdapter(mCityList, this);
            rcvCity.setAdapter(mCityAdapter);
        }

    }

    @Override
    public void sentCityName(String cityName, boolean isCheck) {
        Log.d(TAG, "sentCityName: ");
        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
        intent.putExtra(KeyApi.CITY_NAME_SEARCH, cityName);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void getDataSearch(List<String> cityListString) {
        Log.d(TAG, "getDataSearch: ");
        ArrayAdapter<String> mCities = new ArrayAdapter<>(this
                , android.R.layout.simple_list_item_1, cityListString);
        editSearch.setAdapter(mCities);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivDelete.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editSearch.setOnItemClickListener((parent, view, position, id) -> {
            String mCityName = editSearch.getText().toString().split("\n")[0];
            this.sentCityName(mCityName, true);
            InputMethodManager methodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}