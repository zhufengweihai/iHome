package com.zf.weather.bean;

/**
 * Created by zhufeng7 on 2016-7-20.
 */

public class Environment {
    private String airQuality = null;
    private String pm25 = null;
    private String suggest = null;
    private String quality = null;
    private String pm10 = null;

    public String getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(String airQuality) {
        this.airQuality = airQuality;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getAirQualityBrief() {
        return airQuality + " " + quality;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "airQuality='" + airQuality + '\'' +
                ", pm25='" + pm25 + '\'' +
                ", suggest='" + suggest + '\'' +
                ", quality='" + quality + '\'' +
                ", pm10='" + pm10 + '\'' +
                '}';
    }
}
