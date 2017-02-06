package com.zf.weather.bean;

/**
 * Created by zhufeng7 on 2016-7-20.
 */

public class WeatherType {
    private String type = null;
    private String windDirection = null;
    private String windPower = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    @Override
    public String toString() {
        return "WeatherType{" +
                "type='" + type + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", windPower='" + windPower + '\'' +
                '}';
    }
}
