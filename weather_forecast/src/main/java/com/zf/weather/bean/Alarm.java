package com.zf.weather.bean;

/**
 * Created by zhufeng7 on 2016-7-12.
 */

public class Alarm {
    private String city = null;
    private String alarmType = null;
    private String alarmDegree = null;
    private String alarmText = null;
    private String standard = null;
    private String alarmDetails = null;
    private String suggest = null;
    private String imgUrl = null;
    private String time = null;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmDegree() {
        return alarmDegree;
    }

    public void setAlarmDegree(String alarmDegree) {
        this.alarmDegree = alarmDegree;
    }

    public String getAlarmText() {
        return alarmText;
    }

    public void setAlarmText(String alarmText) {
        this.alarmText = alarmText;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getAlarmDetails() {
        return alarmDetails;
    }

    public void setAlarmDetails(String alarmDetails) {
        this.alarmDetails = alarmDetails;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "city='" + city + '\'' +
                ", alarmType='" + alarmType + '\'' +
                ", alarmDegree='" + alarmDegree + '\'' +
                ", alarmText='" + alarmText + '\'' +
                ", standard='" + standard + '\'' +
                ", alarmDetails='" + alarmDetails + '\'' +
                ", suggest='" + suggest + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
