package com.zf.weather.request;

import com.zf.weather.bean.WeatherForecast;

/**
 * Created by Administrator on 2016/7/9 0009.
 */
public interface WeatherRequestListener {
    void onRequest(WeatherForecast weather);
}
