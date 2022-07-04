package com.thaiduong.test.model;

public class Wind {
    private final int deg;
    private float speed;

    public Wind(float speed, int deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public String direction() {
        if (deg > 303) {
            return "Hướng Tây Bắc";
        } else if (deg > 258) {
            return "Hướng Tây";
        } else if (deg > 213) {
            return "Hướng Tây Nam";
        } else if (deg > 168) {
            return "Hướng Nam";
        } else if (deg > 123) {
            return "Hướng Đông Nam";
        } else if (deg > 78) {
            return "Hướng Đông";
        } else if (deg > 33) {
            return "Hướng Đông Bắc";
        } else {
            return "Hướng Bắc";
        }
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
