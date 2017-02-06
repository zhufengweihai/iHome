package com.zf.lottery.dao.impl;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.zf.common.NoHttpUtils;
import com.zf.lottery.dao.LotteryClassDao;
import com.zf.lottery.dao.LotteryClassListener;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class LotteryClassDaoImpl implements LotteryClassDao {
    private static final String URL = "https://route.showapi.com/44-6?showapi_appid=31607&showapi_timestamp=%s&showapi_sign=4bff9b84bd8f090a12d8c346618d092b";

    @Override
    public void requestLotteryClass(LotteryClassListener listener) {
        String url = String.format(URL, new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()));
        Request request = NoHttp.createStringRequest(url);
        NoHttpUtils.instance().getRequestQueue().add(0, request, new OnResponseListener(){
            @Override
            public void onStart(int what) {

            }

            @Override
            public void onSucceed(int what, Response response) {

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {

            }

            @Override
            public void onFinish(int what) {

            }
        });
    }
}
