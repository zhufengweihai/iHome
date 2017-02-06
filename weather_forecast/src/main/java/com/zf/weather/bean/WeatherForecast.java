package com.zf.weather.bean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/9 0009.
 */
public class WeatherForecast {
    private String city = null;
    private String updateTime = null;
    private String temp = null;
    private String humidity = null;
    private String windPower = null;
    private String windDirection = null;
    private String sunrise = null;
    private String sunset = null;
    private Environment environment = null;
    private Alarm alarm = null;
    private List<DailyWeather> dailyWeathers = new ArrayList<>(6);
    private List<Index> indices = new ArrayList<>();

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindPower() {
        return windPower;
    }

    public void setWindPower(String windPower) {
        this.windPower = windPower;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public List<DailyWeather> getDailyWeathers() {
        return dailyWeathers;
    }

    public void addDailyWeather(DailyWeather dailyWeather) {
        dailyWeathers.add(dailyWeather);
    }

    public List<Index> getIndices() {
        return indices;
    }

    public void addIndex(Index index) {
        indices.add(index);
    }

    public String getWind() {
        return windDirection + windPower;
    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "city='" + city + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", temp='" + temp + '\'' +
                ", humidity='" + humidity + '\'' +
                ", windPower='" + windPower + '\'' +
                ", windDirection='" + windDirection + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", environment=" + environment +
                ", alarm=" + alarm +
                ", dailyWeathers=" + dailyWeathers +
                ", indices=" + indices +
                '}';
    }
}
