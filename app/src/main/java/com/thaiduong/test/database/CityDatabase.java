package com.thaiduong.test.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.thaiduong.test.model.City;

@Database(entities = {City.class}, version = 1)
public abstract class CityDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "city.db";
    private static CityDatabase instance;

    public static synchronized CityDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CityDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CityDAO cityDAO();
}
