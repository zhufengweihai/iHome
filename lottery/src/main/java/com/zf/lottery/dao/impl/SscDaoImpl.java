package com.zf.lottery.dao.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;
import com.zf.lottery.dao.LotteryDao;
import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.data.Lottery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class SscDaoImpl implements LotteryDao {
    private static final String URL_RESULT = "https://route.showapi.com/44-2?code=cqssc&count=50&endTime=%s" +
            "&showapi_appid=31607&showapi_sign=a5858af94b274596b7e175634a2ed269";
    private static final int ONE_MINITE = 60 * 1000;
    private static final int MAX_NUM = 100;
    private static final int MIN_SIZE = 1000;
    private static final int MAX_SIZE = 11000;

    private SimpleDateFormat urlDataFormat = new SimpleDateFormat("yyyy-MM-dd%20HH:mm", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    private LotteryDbHelper dbHelper = null;
    private List<Lottery> historyLotteries = null;
    private List<Lottery> lotteries = new ArrayList<>();

    public SscDaoImpl(Context context) {
        dbHelper = new LotteryDbHelper(context);
    }

    @Override
    public void obtainLotteryResults(final LotteryResultsListener listener) {
        historyLotteries = dbHelper.readSscResult();
        if (historyLotteries.size() < MIN_SIZE) {
            String url = String.format(URL_RESULT, urlDataFormat.format(new Date()));
            requestLotteryResult(listener, url);
        } else {

        }
    }

    private void requestLotteryResult(final LotteryResultsListener listener, String url) {
        Request request = NoHttp.createJsonObjectRequest(url);
        OnResponseListener l = new SimpleResponseListener<JSONObject>() {

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    if (lotteries.size() < MAX_SIZE) {
                        lotteries.addAll(createLotteries(response));
                    } else {
                        dbHelper.saveSscResult(lotteries);
                        listener.onRequest(dbHelper.readSscResult());
                        return;
                    }

                    if (lotteries.size() % 900 == 0) {
                        Thread.sleep(15000);
                    }

                    requestLotteryResult(listener, createUrl());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        NoHttpUtils.instance().addRequest(0, request, l);
    }

    private List<Lottery> createLotteries(Response<JSONObject> response) throws JSONException, ParseException {
        JSONObject jsonObject = response.get();
        JSONArray resultJsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONArray("result");
        int length = resultJsonArray.length();
        List<Lottery> lotteries = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            JSONObject result = resultJsonArray.getJSONObject(i);
            lotteries.add(createLottery(result));
        }
        return lotteries;
    }

    @NonNull
    private Lottery createLottery(JSONObject result) throws JSONException, ParseException {
        Lottery lottery = new Lottery();
        lottery.setTerm(Long.parseLong(result.getString("expect")));
        Date time = dateFormat.parse(result.getString("time").substring(0, 16));
        lottery.setTime(time);
        int[] numbers = toIntArray(result.getString("openCode"));
        lottery.setNumbers(numbers);
        lottery.setSum(numbers[3] * 10 + numbers[4]);
        return lottery;
    }

    private String createUrl() {
        Date date = lotteries.get(lotteries.size() - 1).getTime();
        date.setTime(date.getTime() - ONE_MINITE);
        return String.format(URL_RESULT, urlDataFormat.format(date));
    }

    private void calcMaxAbence(List<Lottery> historyLotteries, List<Lottery> lotteries) {
        int[] occurArray = calcMaxAbence(historyLotteries);
        int l = lotteries.size() - 1;
        for (int i = l; i >= 0; i--) {
            Lottery lottery = lotteries.get(i);
            for (int j = 0; j < MAX_NUM; j++) {
                occurArray[j]++;
            }
            occurArray[lottery.getSum()] = 0;
            lotteries.get(i).setMaxAbence(max(occurArray));
        }
    }

    private int[] calcMaxAbence(List<Lottery> lotteries) {
        int[] occurArray = new int[MAX_NUM];
        int l = lotteries.size() - 1;
        for (int i = l; i >= 0; i--) {
            Lottery lottery = lotteries.get(i);
            for (int j = 0; j < MAX_NUM; j++) {
                occurArray[j]++;
            }
            occurArray[lottery.getSum()] = 0;
        }
        return occurArray;
    }

    private int max(int[] input) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < input.length; i++) {
            if (max < input[i]) {
                max = input[i];
            }
        }
        return max;
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
