package com.zf.lottery.dao.impl;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;
import com.zf.lottery.dao.LotteryClassDao;
import com.zf.lottery.dao.LotteryClassListener;
import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.data.LotteryClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class LotteryClassDaoImpl implements LotteryClassDao {
    private static final String URL = "https://route.showapi" + "" + "" + "" +
            ".com/44-6?showapi_appid=31607&showapi_sign=a5858af94b274596b7e175634a2ed269";
    private static final String URL_RESULT = "https://route.showapi" + "" + "" + "" +
            ".com/44-2?code=cqssc&count=50&endTime=%s&showapi_appid=31607" +
            "&showapi_sign=a5858af94b274596b7e175634a2ed269";

    @Override
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
    public void requestLotteryResults(final LotteryResultsListener listener) {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final List<Lottery> lotteries = new ArrayList<Lottery>();
        for (int i = 0; i < 10; i++) {
            Date afterDate = new Date(now.getTime() + i * 10 * 50 * 60 * 1000);
            String url = String.format(URL_RESULT, dateFormat.format(afterDate));
            Request request = NoHttp.createJsonObjectRequest(url);
            NoHttpUtils.instance().addRequest(0, request, new SimpleResponseListener<JSONObject>() {

                @Override
                public void onSucceed(int what, Response<JSONObject> response) {
                    try {
                        JSONObject jsonObject = response.get();
                        JSONArray resultJsonArray = jsonObject.getJSONObject("showapi_res_body").getJSONArray("result");
                        int length = resultJsonArray.length();

                        for (int i = 0; i < length; i++) {
                            JSONObject result = resultJsonArray.getJSONObject(i);
                            Lottery lottery = new Lottery();
                            lottery.setTerm(result.getString("expect"));
                            lottery.setTime(result.getString("time"));
                            lottery.setNumbers(result.getString("openCode").split(","));
                            lotteries.add(lottery);
                        }
                        listener.onRequest(lotteries);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(int what, Response<JSONObject> response) {
                    super.onFailed(what, response);
                }
            });
        }

    }
}
