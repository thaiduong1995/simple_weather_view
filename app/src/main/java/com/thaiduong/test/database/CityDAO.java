package com.thaiduong.test.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.thaiduong.test.model.City;

import java.util.List;

@Dao
public interface CityDAO {
    @Insert
    void insertCity(City city);

    @Query("select * from city")
    List<City> getListCity();
}
