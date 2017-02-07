package com.zf.lottery.parse;

import android.support.annotation.NonNull;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;
import com.zf.lottery.LotteryTerm;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhufeng7 on 2016-6-29.
 */

public class LotteryInfoRequest {
    public static final String LOTTERY_CLASS_NOTES = "notes";
    private static final String URL_HEAD = "http://f.apiplus.cn/";
    public static final String URL_TAIL = ".json";
    public static final String LOTTERY_TYPE_SEQ = "seq";
    public static final String LOTTERY_TYPE_DESCR = "descr";
    public static final String LOTTERY_CLASS_CODE = "code";
    public static final String LOTTERY_CLASS_TDESCR = "tdescr";
    public static final String LOTTERY_CLASS_DESCR = "descr";

    public void request(Lottery lottery, LotteryInfoResolveListener listener) {
        Request request = NoHttp.createJsonObjectRequest(URL_HEAD + lottery.getCode() + URL_TAIL);
        RequestQueue requestQueue = NoHttpUtils.instance().requestQueue();
        requestQueue.add(NoHttpUtils.getWhat(), request, createResponseListener(lottery, listener));
    }

    @NonNull
    SimpleResponseListener<JSONObject> createResponseListener(final Lottery lottery, final LotteryInfoResolveListener
            listener) {
        return new SimpleResponseListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject lotteriesInfo = response.get();
                try {
                    JSONArray dataArray = lotteriesInfo.getJSONArray("data");
                    int length = dataArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject data = dataArray.getJSONObject(i);
                        int expect = data.getInt("expect");
                        String opencode = data.getString("opencode");
                        String opentime = data.getString("opentime");
                        LotteryTerm term = createLotteryTerm(expect, opencode, opentime);
                        lottery.addTerm(term);
                    }

                    listener.onResolved(lottery);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @NonNull
    private LotteryTerm createLotteryTerm(int expect, String opencode, String opentime) {
        LotteryTerm term = new LotteryTerm();
        term.setTerm(expect);
        term.setOpenTime(opentime);
        String[] codes = StringUtils.split(opencode, '+');
        if (codes != null && codes.length > 0) {
            if (codes[0] != null) {
                String[] redCodes = StringUtils.split(codes[0], ',');
                term.addRedCode(redCodes);
            }
            if (codes.length > 1 && codes[1] != null) {
                String[] blueCodes = StringUtils.split(codes[1], ',');
                term.addBlueCode(blueCodes);
            }
        }
        return term;
    }
}
