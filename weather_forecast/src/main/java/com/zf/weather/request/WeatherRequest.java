package com.zf.weather.request;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Xml;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;
import com.zf.weather.bean.Alarm;
import com.zf.weather.bean.DailyWeather;
import com.zf.weather.bean.Environment;
import com.zf.weather.bean.Index;
import com.zf.weather.bean.WeatherForecast;
import com.zf.weather.bean.WeatherType;

import org.apache.commons.lang3.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by zhufeng7 on 2016-6-29.
 */

public class WeatherRequest {
    private static final String URL = "http://wthrcdn.etouch.cn/WeatherApi?citykey=";

    public void request(String city, WeatherRequestListener listener) {
        Request request = NoHttp.createStringRequest(URL + city);
        RequestQueue requestQueue = NoHttpUtils.instance().requestQueue();
        requestQueue.add(NoHttpUtils.getWhat(), request, createResponseListener(listener));
    }

    @NonNull
    private SimpleResponseListener<String> createResponseListener(final WeatherRequestListener listener) {
        return new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String xml = response.get();
                Log.e("weather", xml);
                try {
                    WeatherForecast forecast = new WeatherForecast();
                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(new StringReader(xml));
                    parser.next();
                    parser.require(XmlPullParser.START_TAG, null, "resp");
                    forecast.setCity(nextTagAndText(parser));
                    forecast.setUpdateTime(nextTagAndText(parser));
                    forecast.setTemp(nextTagAndText(parser));
                    forecast.setWindPower(nextTagAndText(parser));
                    forecast.setHumidity(nextTagAndText(parser));
                    forecast.setWindDirection(nextTagAndText(parser));
                    forecast.setSunrise(nextTagAndText(parser));
                    forecast.setSunset(nextTagAndText(parser));
                    nextElement(parser, 2);
                    Environment environment = parseEnvironment(parser);
                    forecast.setEnvironment(environment);
                    parser.next();
                    if ("yesterday".equals(parser.getName())) {
                        DailyWeather dailyWeather = parseYesterday(parser);
                        forecast.addDailyWeather(dailyWeather);
                    } else {
                        Alarm alarm = parseAlarm(parser);
                        forecast.setAlarm(alarm);
                        parser.next();
                        DailyWeather dailyWeather = parseYesterday(parser);
                        forecast.addDailyWeather(dailyWeather);
                    }

                    while (parser.nextTag() != XmlPullParser.END_TAG) {
                        parser.require(XmlPullParser.START_TAG, null, "forecast");
                        for (int i = 0; i < 5; i++) {
                            while (parser.nextTag() != XmlPullParser.END_TAG) {
                                parser.require(XmlPullParser.START_TAG, null, "weather");
                                DailyWeather dailyWeather = parseDailyWeather(parser);
                                forecast.addDailyWeather(dailyWeather);
                            }
                        }
                    }
                    while (parser.nextTag() != XmlPullParser.END_TAG) {
                        parser.require(XmlPullParser.START_TAG, null, "zhishus");
                        for (int i = 0; i < 11; i++) {
                            while (parser.nextTag() != XmlPullParser.END_TAG) {
                                parser.require(XmlPullParser.START_TAG, null, "zhishu");
                                Index index = new Index();
                                index.setName(nextTagAndText(parser));
                                index.setValue(nextTagAndText(parser));
                                index.setDetails(nextTagAndText(parser));
                                forecast.addIndex(index);
                            }
                        }
                    }

                    listener.onRequest(forecast);
                } catch (XmlPullParserException | IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private void nextElement(XmlPullParser parser, int count) throws IOException, XmlPullParserException {
        for (int i = 0; i < count; i++) {
            parser.next();
            if (parser.next() != XmlPullParser.END_TAG) {
                parser.next();
            }
        }
    }

    private String nextTagAndText(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.nextTag();
        return parser.nextText();
    }

    private void requestEnd(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.next();
        parser.require(XmlPullParser.END_TAG, null, tag);
    }

    private Environment parseEnvironment(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.next();
        parser.require(XmlPullParser.START_TAG, null, "environment");
        Environment environment = new Environment();
        environment.setAirQuality(nextTagAndText(parser));
        environment.setPm25(nextTagAndText(parser));
        environment.setSuggest(nextTagAndText(parser));
        environment.setQuality(nextTagAndText(parser));
        nextElement(parser, 3);
        environment.setPm10(nextTagAndText(parser));
        nextElement(parser, 3);
        requestEnd(parser, "environment");
        return environment;
    }

    private Alarm parseAlarm(XmlPullParser parser) throws IOException, XmlPullParserException {
        Alarm alarm = new Alarm();
        nextElement(parser, 1);
        alarm.setCity(nextTagAndText(parser));
        alarm.setAlarmType(nextTagAndText(parser));
        alarm.setAlarmDegree(nextTagAndText(parser));
        alarm.setAlarmText(nextTagAndText(parser));
        alarm.setAlarmDetails(nextTagAndText(parser));
        alarm.setStandard(nextTagAndText(parser));
        alarm.setSuggest(nextTagAndText(parser));
        alarm.setImgUrl(nextTagAndText(parser));
        alarm.setTime(nextTagAndText(parser));
        requestEnd(parser, "alarm");
        return alarm;
    }

    @NonNull
    DailyWeather parseYesterday(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "yesterday");
        DailyWeather dailyWeather = parseDailyWeather(parser);
        requestEnd(parser, "yesterday");
        return dailyWeather;
    }

    @NonNull
    private DailyWeather parseDailyWeather(XmlPullParser parser) throws IOException, XmlPullParserException {
        DailyWeather dailyWeather = new DailyWeather();
        dailyWeather.setDate(nextTagAndText(parser));
        String highTemp = StringUtils.substringBetween(nextTagAndText(parser), "高温 ", "℃");
        dailyWeather.setHighTemp(highTemp);
        String lowTemp = StringUtils.substringBetween(nextTagAndText(parser), "低温 ", "℃");
        dailyWeather.setLowTemp(lowTemp);
        while (parser.nextTag() != XmlPullParser.END_TAG) {
            WeatherType dayWeather = new WeatherType();
            dayWeather.setType(nextTagAndText(parser));
            dayWeather.setWindDirection(nextTagAndText(parser));
            dayWeather.setWindPower(nextTagAndText(parser));
            dailyWeather.setDayWeather(dayWeather);
        }
        while (parser.nextTag() != XmlPullParser.END_TAG) {
            WeatherType dayWeather = new WeatherType();
            dayWeather.setType(nextTagAndText(parser));
            dayWeather.setWindDirection(nextTagAndText(parser));
            dayWeather.setWindPower(nextTagAndText(parser));
            dailyWeather.setNightWeather(dayWeather);
        }
        return dailyWeather;
    }
}
