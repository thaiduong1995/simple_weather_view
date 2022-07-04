package com.thaiduong.test.model;

import java.util.List;

public class Locations {
    private List<LatLng> locations;

    public Locations(List<LatLng> locations) {
        this.locations = locations;
    }

    public List<LatLng> getLocations() {
        return locations;
    }

    public void setLocations(List<LatLng> locations) {
        this.locations = locations;
    }
}
