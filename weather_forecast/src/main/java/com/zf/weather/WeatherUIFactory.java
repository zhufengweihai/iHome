package com.zf.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Created by zhufeng7 on 2016-8-19.
 */

public class WeatherUIFactory {
    public static Drawable getWeatherBackground(Context context, boolean isNight, String type) {
        Drawable image = null;
        int id = -1;
        if (isNight) {
            if ("晴".equals(type)) {
                id = R.drawable.background_for_star_night;
            } else if ("多云".equals(type)) {
                id = R.drawable.background_for_cloud_night;
            } else if ("雷阵雨".equals(type)) {
                id = R.drawable.background_for_thunderstorm_night;
            } else if ("小雨".equals(type) || "阵雨".equals(type)) {
                id = R.drawable.background_for_light_rain_night;
            } else if ("阴".equals(type)) {
                id = R.drawable.background_for_overcast_night;
            }
        } else {
            if ("晴".equals(type)) {
                id = R.drawable.background_for_sun;
            } else if ("多云".equals(type)) {
                id = R.drawable.background_for_cloud;
            } else if ("雷阵雨".equals(type)) {
                id = R.drawable.background_for_thunderstorm;
            } else if ("小雨".equals(type) || "阵雨".equals(type)) {
                id = R.drawable.background_for_light_rain;
            } else if ("阴".equals(type)) {
                id = R.drawable.background_for_overcast;
            }
        }

        if (id > 0) {
            image = getDrawable(context, id);
        }

        return image;
    }

    public static Drawable getWeatherAboveIcon(Context context, String type, boolean isNight) {
        Drawable image = null;
        if ("晴".equals(type)) {
            image = getDrawable(context, R.mipmap.ic_weather_sun);
        } else if ("多云".equals(type)) {
            image = getDrawable(context, R.mipmap.ic_weather_above_cloudy);
        } else if ("雷阵雨".equals(type)) {
            image = getDrawable(context, R.mipmap.ic_weather_above_thundershower);
            int color = context.getResources().getColor(isNight ? R.color.showerNightEnd : R.color.showerEnd);
            DrawableCompat.setTint(image, color);
        } else if ("小雨".equals(type) || "阵雨".equals(type)) {
            image = getDrawable(context, R.mipmap.ic_weather_above_lightrain);
            int color = context.getResources().getColor(isNight ? R.color.lightRainNightEnd : R.color.lightRainEnd);
            DrawableCompat.setTint(image, color);
        } else if ("阴".equals(type)) {
            image = getDrawable(context, R.mipmap.ic_weather_overcast);
            int color = context.getResources().getColor(isNight ? R.color.overcastNightEnd : R.color.overcastEnd);
            DrawableCompat.setTint(image, color);
        }

        if (image != null) {
            int h = image.getIntrinsicHeight();
            int w = image.getIntrinsicWidth();
            image.setBounds(0, 0, w, h);
        }

        return image;
    }

    public static Drawable getWeatherBelowIcon(Context context, String type, boolean isNight) {
        Drawable image = null;
        if ("多云".equals(type)) {
            image = getDrawable(context, R.mipmap.ic_weather_below_cloudy);
            int color = context.getResources().getColor(isNight ? R.color.cloudyNightEnd : R.color.cloudyEnd);
            DrawableCompat.setTint(image, color);
        } else if ("雷阵雨".equals(type)) {
            image = getDrawable(context, R.mipmap.ic_weather_below_thundershower);
        } else if ("小雨".equals(type) || "阵雨".equals(type)) {
            image = getDrawable(context, R.mipmap.ic_weather_below_lightrain);
        }

        if (image != null) {
            int h = image.getIntrinsicHeight();
            int w = image.getIntrinsicWidth();
            image.setBounds(0, 0, w, h);
        }

        return image;
    }

    private static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getDrawable(id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
}
