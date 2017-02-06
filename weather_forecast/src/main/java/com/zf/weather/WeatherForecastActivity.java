package com.zf.weather;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.zf.common.app.BaseActivity;
import com.zf.weather.bean.DailyWeather;
import com.zf.weather.bean.Environment;
import com.zf.weather.bean.Index;
import com.zf.weather.bean.WeatherForecast;
import com.zf.weather.request.CityRequest;
import com.zf.weather.request.CityRequestListener;
import com.zf.weather.request.WeatherRequest;
import com.zf.weather.request.WeatherRequestListener;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherForecastActivity extends BaseActivity implements WeatherRequestListener, CityRequestListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        StatusBarUtil.setTranslucentForCoordinatorLayout(this, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Calendar c = Calendar.getInstance();
        int week = c.get(Calendar.DAY_OF_WEEK) + 1;
        String[] weeks = getResources().getStringArray(R.array.weeks);
        String weekString = weeks[week >= 7 ? week - 7 : week];
        TextView dayView = (TextView) findViewById(R.id.day4View);
        dayView.setText(weekString);
        weekString = weeks[++week >= 7 ? week - 7 : week];
        dayView = (TextView) findViewById(R.id.day5View);
        dayView.setText(weekString);
        weekString = weeks[++week >= 7 ? week - 7 : week];
        dayView = (TextView) findViewById(R.id.day6View);
        dayView.setText(weekString);

        CityRequest cityRequest = new CityRequest(this);
        cityRequest.request(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }


    @Override
    public void onRequest(String city, String cityCode) {
        ((TextView) findViewById(R.id.cityView)).setText(city);
        WeatherRequest weatherRequest = new WeatherRequest();
        weatherRequest.request(cityCode, this);
    }

    @Override
    public void onRequest(WeatherForecast weather) {
        boolean isNight = isNight(weather.getSunset());
        String type = getWeatherType(weather, isNight);

        updateBackground(type, isNight);
        ((TextView) findViewById(R.id.weatherView)).setText(type);
        ((TextView) findViewById(R.id.tempView)).setText(weather.getTemp() + "℃");
        updateAlarm(weather);
        updateTempRange(weather);
        ((TextView) findViewById(R.id.windView)).setText(weather.getWind());
        ((TextView) findViewById(R.id.pm25View)).setText(weather.getEnvironment().getAirQualityBrief());
        updateDailyWeathers(weather, isNight);

        Environment environment = weather.getEnvironment();
        View aqiDetailsLayout = findViewById(R.id.aqiDetailsLayout);
        TextView qualityView = (TextView) aqiDetailsLayout.findViewById(R.id.qualityView);
        qualityView.setText(environment.getQuality());
        TextView aqiView = (TextView) aqiDetailsLayout.findViewById(R.id.aqiView);
        aqiView.setText(environment.getAirQualityBrief());
        TextView pm25AqiView = (TextView) aqiDetailsLayout.findViewById(R.id.pm25AqiView);
        pm25AqiView.setText(environment.getPm25());
        ProgressBar pm25PercentView = (ProgressBar) aqiDetailsLayout.findViewById(R.id.pm25PercentView);
        pm25PercentView.setProgress(Integer.parseInt(environment.getPm25()));
        TextView pm10AqiView = (TextView) aqiDetailsLayout.findViewById(R.id.pm10AqiView);
        pm10AqiView.setText(environment.getPm10());
        ProgressBar pm10PercentView = (ProgressBar) aqiDetailsLayout.findViewById(R.id.pm10PercentView);
        pm10PercentView.setProgress(Integer.parseInt(environment.getPm10()));
        TextView suggestView = (TextView) aqiDetailsLayout.findViewById(R.id.suggestView);
        suggestView.setText(environment.getSuggest());

        View weatherMoreLayout = findViewById(R.id.weatherMoreLayout);
        TextView windDirectionView = (TextView) weatherMoreLayout.findViewById(R.id.windDirectionView);
        windDirectionView.setText(weather.getWindDirection());
        TextView windPowerView = (TextView) weatherMoreLayout.findViewById(R.id.windPowerView);
        windPowerView.setText(weather.getWindPower());
        TextView sunriseView = (TextView) weatherMoreLayout.findViewById(R.id.sunriseView);
        sunriseView.setText(weather.getSunrise());
        TextView sunsetView = (TextView) weatherMoreLayout.findViewById(R.id.sunsetView);
        sunsetView.setText(weather.getSunset());
        TextView humidityView = (TextView) weatherMoreLayout.findViewById(R.id.humidityView);
        humidityView.setText(weather.getHumidity());
        TextView apparentTemperatureView = (TextView) weatherMoreLayout.findViewById(R.id.apparentTemperatureView);
        apparentTemperatureView.setText(weather.getTemp());

        List<Index> indices = weather.getIndices();
        View suggestLayout = findViewById(R.id.suggestLayout);
        TextView comfortView = (TextView) suggestLayout.findViewById(R.id.comfortView);
        comfortView.setText(indices.get(1).getValue());
        TextView dressView = (TextView) suggestLayout.findViewById(R.id.dressView);
        dressView.setText(indices.get(2).getValue());
        TextView coldView = (TextView) suggestLayout.findViewById(R.id.coldView);
        coldView.setText(indices.get(3).getValue());
        TextView ultravioletView = (TextView) suggestLayout.findViewById(R.id.ultravioletView);
        ultravioletView.setText(indices.get(6).getValue());
        TextView carWashView = (TextView) suggestLayout.findViewById(R.id.carWashView);
        carWashView.setText(indices.get(7).getValue());
        TextView sportView = (TextView) suggestLayout.findViewById(R.id.sportView);
        sportView.setText(indices.get(8).getValue());
        TextView dateView = (TextView) suggestLayout.findViewById(R.id.dateView);
        dateView.setText(indices.get(9).getValue());
        TextView umbrellaView = (TextView) suggestLayout.findViewById(R.id.umbrellaView);
        umbrellaView.setText(indices.get(10).getValue());
    }

    void updateDailyWeathers(WeatherForecast weather, boolean isNight) {
        updateDailyWeather(weather, isNight, R.id.temp1View, R.id.weather1View, 0);
        updateDailyWeather(weather, isNight, R.id.temp2View, R.id.weather2View, 1);
        updateDailyWeather(weather, isNight, R.id.temp3View, R.id.weather3View, 2);
        updateDailyWeather(weather, isNight, R.id.temp4View, R.id.weather4View, 3);
        updateDailyWeather(weather, isNight, R.id.temp5View, R.id.weather5View, 4);
        updateDailyWeather(weather, isNight, R.id.temp6View, R.id.weather6View, 5);
    }

    void updateTempRange(WeatherForecast weather) {
        DailyWeather todayWeather = weather.getDailyWeathers().get(1);
        String tempRange = todayWeather.getLowTemp() + "～" + todayWeather.getHighTemp() + "℃";
        ((TextView) findViewById(R.id.tempRangeView)).setText(tempRange);
    }

    void updateAlarm(WeatherForecast weather) {
        TextView alarmView = (TextView) findViewById(R.id.alarmView);
        if (weather.getAlarm() != null) {
            alarmView.setText(weather.getAlarm().getAlarmType() + getString(R.string.alarm));
            alarmView.setVisibility(View.VISIBLE);
        } else {
            alarmView.setVisibility(View.GONE);
        }
    }

    void updateBackground(String type, boolean isNight) {
        View rootView = findViewById(R.id.rootView);
        Drawable weatherBackground = WeatherUIFactory.getWeatherBackground(this, isNight, type);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            rootView.setBackground(weatherBackground);
        } else {
            rootView.setBackgroundDrawable(weatherBackground);
        }
    }

    private String getWeatherType(WeatherForecast weather, boolean isNight) {
        if (isNight) {
            return weather.getDailyWeathers().get(1).getDayWeather().getType();
        }

        return weather.getDailyWeathers().get(1).getNightWeather().getType();
    }

    private boolean isNight(String sunsetTime) {
        try {
            if (new Date().after(DateUtils.parseDate(sunsetTime, "mm:ss"))) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    void updateDailyWeather(WeatherForecast weather, boolean isNight, int tempViewId, int weatherViewId, int i) {
        DailyWeather dailyWeather = weather.getDailyWeathers().get(i);
        ImageView weatherView = (ImageView) findViewById(weatherViewId);
        String type = null;
        if (isNight) {
            type = dailyWeather.getNightWeather().getType();
        } else {
            type = dailyWeather.getDayWeather().getType();
        }
        weatherView.setImageDrawable(WeatherUIFactory.getWeatherAboveIcon(this, type, isNight));
        weatherView.setBackgroundDrawable(WeatherUIFactory.getWeatherBelowIcon(this, type, isNight));
        TextView tempView = (TextView) findViewById(tempViewId);
        tempView.setText(dailyWeather.getTempInfo());
    }
}
