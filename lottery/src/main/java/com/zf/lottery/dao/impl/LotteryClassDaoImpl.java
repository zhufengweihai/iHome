package com.zf.lottery.dao.impl;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;
import com.zf.lottery.dao.LotteryClassDao;
import com.zf.lottery.dao.LotteryClassListener;
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
    private static final String URL = "https://route.showapi.com/44-6?showapi_appid=31607&showapi_sign=a5858af94b274596b7e175634a2ed269";

    @Override
    public void requestLotteryClass(final LotteryClassListener listener) {
        //String url = String.format(URL, new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        final Request request = NoHttp.createJsonObjectRequest(URL);
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
}
