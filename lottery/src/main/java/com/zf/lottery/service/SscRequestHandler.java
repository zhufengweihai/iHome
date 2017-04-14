package com.zf.lottery.service;

import android.util.Log;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;
import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.data.Lottery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/3/26 0026.
 */

public class SscRequestHandler {
    private static final String URL_RESULT = "http://route.showapi.com/44-2?code=cqssc&count=50&endTime=%s" +
            "&showapi_appid=31607&showapi_sign=a5858af94b274596b7e175634a2ed269";

    private SimpleDateFormat urlDateFormat = new SimpleDateFormat("yyyy-MM-dd%20HH:mm", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public void requestLottery(Date time, final LotteryResultsListener listener) {
        String url = String.format(URL_RESULT, urlDateFormat.format(time));
        Request request = NoHttp.createJsonObjectRequest(url);
        final OnResponseListener l = new SimpleResponseListener<JSONObject>() {

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    listener.onRequest(createLotteries(response));
                } catch (Exception e) {
                    Log.e("SscRequestHandler", "Failed to request data", e);
                }
            }

            @Override
            public void onFailed(int what, Response<JSONObject> response) {
                Log.e("SscRequestHandler", response.toString());
            }
        };
        NoHttpUtils.instance().addRequest(0, request, l);
    }

    private List<Lottery> createLotteries(Response<JSONObject> response) throws Exception {
        JSONObject jsonObject = response.get();
        JSONArray resultJsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONArray("result");
        int length = resultJsonArray.length();
        List<Lottery> lotteries = new ArrayList<>(50);
        for (int i = 0; i < length; i++) {
            JSONObject result = resultJsonArray.getJSONObject(i);
            Lottery lottery = createLottery(result);
            if (!lotteries.contains(lottery)) {
                lotteries.add(lottery);
            }
        }
        return lotteries;
    }

    private Lottery createLottery(JSONObject result) throws Exception {
        Lottery lottery = new Lottery();
        lottery.setTerm(Long.parseLong(result.getString("expect")));
        Date time = dateFormat.parse(result.getString("time").substring(0, 16));
        lottery.setTime(time);
        int[] numbers = toIntArray(result.getString("openCode"));
        lottery.setNumbers(numbers);
        lottery.setSum(numbers[3] * 10 + numbers[4]);
        return lottery;
    }

    private int[] toIntArray(String string) {
        String[] numberStrings = string.split(",");
        int[] numbers = new int[numberStrings.length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(numberStrings[i]);
        }
        return numbers;
    }
}
