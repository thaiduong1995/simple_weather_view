package com.thaiduong.test.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "city")
public class City {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String country;

    public City(String name, String country) {
        this.name = name;
        this.country = country;
    }

    public boolean compare(City city) {
        return name.equals(city.getName()) && country.equals(city.getCountry());
    }

    @NonNull
    @Override
    public String toString() {
        return name + "\n" + country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
