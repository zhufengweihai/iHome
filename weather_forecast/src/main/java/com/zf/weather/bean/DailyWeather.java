package com.zf.weather.bean;

/**
 * Created by zhufeng7 on 2016-7-12.
 */

public class DailyWeather {
    private String date = null;
    private String highTemp = null;
    private String lowTemp = null;
    private WeatherType dayWeather = null;
    private WeatherType nightWeather = null;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public WeatherType getDayWeather() {
        return dayWeather;
    }

    public void setDayWeather(WeatherType dayWeather) {
        this.dayWeather = dayWeather;
    }

    public WeatherType getNightWeather() {
        return nightWeather;
    }

    public void setNightWeather(WeatherType nightWeather) {
        this.nightWeather = nightWeather;
    }

    public String getTempInfo() {
        return lowTemp + "°/" + highTemp + '°';
    }

    @Override
    public String toString() {
        return "DailyWeather{" +
                "date='" + date + '\'' +
                ", highTemp='" + highTemp + '\'' +
                ", lowTemp='" + lowTemp + '\'' +
                ", dayWeather=" + dayWeather +
                ", nightWeather=" + nightWeather +
                '}';
    }
}
