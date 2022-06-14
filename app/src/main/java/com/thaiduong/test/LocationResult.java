package com.thaiduong.test;

import java.util.List;

public class LocationResult {
    private List<Locations> results;

    public LocationResult(List<Locations> results) {
        this.results = results;
    }

    public List<Locations> getResults() {
        return results;
    }

    public void setResults(List<Locations> results) {
        this.results = results;
    }
}
