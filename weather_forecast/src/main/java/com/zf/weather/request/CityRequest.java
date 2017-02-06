package com.zf.weather.request;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by zhufeng7 on 2016-6-29.
 */

public class CityRequest {
    private static final String URL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js";

    private Context context = null;

    public CityRequest(Context context) {
        this.context = context;
    }

    public void request(CityRequestListener listener) {
        Request request = NoHttp.createStringRequest(URL);
        RequestQueue requestQueue = NoHttpUtils.instance().getRequestQueue();
        requestQueue.add(NoHttpUtils.getWhat(), request, createResponseListener(listener));
    }

    @NonNull
    private SimpleResponseListener<String> createResponseListener(final CityRequestListener listener) {
        return new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String js = response.get();
                String string = StringUtils.substringBetween(js, "=", ";");
                try {
                    JSONObject ipObject = new JSONObject(string);
                    int ret = ipObject.getInt("ret");
                    if (ret == 1) {
                        String city = ipObject.getString("city");
                        listener.onRequest(city, getCityCode(city));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long
                    networkMillis) {

            }
        };
    }

    private String getCityCode(String city) {
        try {
            InputStream is = context.getAssets().open("city.json", Context.MODE_PRIVATE);
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            String temp = new String(buffer);
            JSONArray cityArray = new JSONArray(temp);
            int l = cityArray.length();
            for (int i = 0; i < l; i++) {
                JSONObject jsonObject = cityArray.getJSONObject(i);
                if (city.equals(jsonObject.getString("city"))) {
                    return jsonObject.getString("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "101010100";
    }
}
