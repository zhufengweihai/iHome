package com.zf.lottery.dao.impl;

import android.os.Environment;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;
import com.zf.lottery.dao.LotteryClassListener;
import com.zf.lottery.dao.LotteryDao;
import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.data.LotteryClass;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class SscDaoImpl implements LotteryDao {
    private static final String URL = "https://route.showapi.com/44-6?showapi_appid=31607&showapi_sign" +
            "=a5858af94b274596b7e175634a2ed269";
    private static final String URL_RESULT = "https://route.showapi.com/44-2?code=cqssc&count=50&endTime=%s" +
            "&showapi_appid=31607&showapi_sign=a5858af94b274596b7e175634a2ed269";
    public static final int ONE_MINITE = 60 * 1000;

    private SimpleDateFormat urlDataFormat = new SimpleDateFormat("yyyy-MM-dd%20HH:mm", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public void requestLotteryClass(final LotteryClassListener listener) {
        //String url = String.format(URL, new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        Request request = NoHttp.createJsonObjectRequest(URL);
        NoHttpUtils.instance().addRequest(0, request, new SimpleResponseListener<JSONObject>() {

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    JSONArray resultJsonArray = response.get().getJSONObject("showapi_res_body").getJSONArray("result");
                    int length = resultJsonArray.length();
                    List<LotteryClass> lotteryClasses = new ArrayList<LotteryClass>(length);
                    for (int i = 0; i < length; i++) {
                        JSONObject result = resultJsonArray.getJSONObject(i);
                        LotteryClass lotteryClass = new LotteryClass();
                        lotteryClass.setName(result.getString("area") + result.getString("descr"));
                        lotteryClass.setCode(result.getString("code"));
                        lotteryClass.setNotes(result.getString("notes"));
                        lotteryClass.setType(result.getString("tdescr"));
                        lotteryClasses.add(lotteryClass);
                    }
                    listener.onRequest(lotteryClasses);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void obtainLotteryResults(final LotteryResultsListener listener) {
        List<Lottery> historyResults = obtainHistoryResults();
        List<Lottery> lotteries = new ArrayList<>();
        String url = String.format(URL_RESULT, urlDataFormat.format(new Date()));
        requestLotteryResult(listener, historyResults, lotteries, url);
    }

    private List<Lottery> obtainHistoryResults() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "/home/cqssc.txt");
            List<String> lines = FileUtils.readLines(file, "UTF-8");
            List<Lottery> lotteries = new ArrayList<>(lines.size());
            for (String line : lines) {
                Lottery lottery = new Lottery();
                String[] strings = line.split(";");
                lottery.setTime(dateFormat.parse(strings[0]));
                int[] numbers = toIntArray(strings[1]);
                lottery.setNumbers(numbers);
                lottery.setSum(numbers[3] * 10 + numbers[4]);
                lotteries.add(lottery);
            }
            return lotteries;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void requestLotteryResult(final LotteryResultsListener listener, final List<Lottery> historyResults,
                                      final List<Lottery> lotteries, String url) {
        Request request = NoHttp.createJsonObjectRequest(url);
        NoHttpUtils.instance().addRequest(0, request, new SimpleResponseListener<JSONObject>() {

            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                try {
                    JSONObject jsonObject = response.get();
                    JSONArray resultJsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONArray("result");
                    int length = resultJsonArray.length();
                    Lottery lastLottery = historyResults.get(0);
                    for (int i = 0; i < length; i++) {
                        JSONObject result = resultJsonArray.getJSONObject(i);
                        Date time = dateFormat.parse(result.getString("time").substring(0, 16));
                        if (time.compareTo(lastLottery.getTime()) > 0) {
                            Lottery lottery = new Lottery();
                            lottery.setTerm(result.getString("expect"));
                            lottery.setTime(time);
                            int[] numbers = toIntArray(result.getString("openCode"));
                            lottery.setNumbers(numbers);
                            lottery.setSum(numbers[3] * 10 + numbers[4]);
                            lotteries.add(lottery);
                        } else {
                            lotteries.addAll(historyResults);
                            saveHistoryResults(lotteries);
                            listener.onRequest(lotteries);
                            return;
                        }
                    }

                    if (lotteries.size() % 900 == 0) {
                        Thread.sleep(15000);
                    }
                    Date date = lotteries.get(lotteries.size() - 1).getTime();
                    date.setTime(date.getTime() - ONE_MINITE);
                    String url = String.format(URL_RESULT, urlDataFormat.format(date));
                    requestLotteryResult(listener, historyResults, lotteries, url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private int[] toIntArray(String string) {
        String[] numberStrings = string.split(",");
        int[] numbers = new int[numberStrings.length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(numberStrings[i]);
        }
        return numbers;
    }

    private void saveHistoryResults(List<Lottery> historyResults) {
        String historyPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/home/cqssc.txt";
        try {
            List<String> lines = new ArrayList<>(historyResults.size());
            for (Lottery result : historyResults) {
                lines.add(toString(result));
            }
            FileUtils.writeLines(new File(historyPath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toString(Lottery result) {
        int[] numbers = result.getNumbers();
        int iMax = numbers.length - 1;
        StringBuilder sb = new StringBuilder();
        sb.append(dateFormat.format(result.getTime())).append(';');
        for (int i = 0; ; i++) {
            sb.append(numbers[i]);
            if (i == iMax) return sb.toString();
            sb.append(',');
        }
    }
}
