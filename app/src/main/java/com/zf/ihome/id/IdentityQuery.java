package com.zf.ihome.id;

import android.support.annotation.NonNull;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/8/7 0007.
 */

public class IdentityQuery {
    private static final String httpUrl = "http://apis.baidu.com/apistore/idservice/id?id=";

    public void request(String idNo,IdentityQueryListener listener) {
        Request<JSONObject> request = NoHttp.createJsonObjectRequest(httpUrl + idNo);
        request.addHeader("apikey", "4bb8e732d276dfa510fa7388ecefa7a0");
        NoHttpUtils.instance().requestQueue().add(NoHttpUtils.getWhat(), request, createIdResponseListener(listener));
    }

    @NonNull
    SimpleResponseListener<JSONObject> createIdResponseListener(final IdentityQueryListener listener) {
        return new SimpleResponseListener<JSONObject>() {
            @Override
            public void onSucceed(int what, Response<JSONObject> response) {
                JSONObject json = response.get();
                try {
                    String retMsg = json.getString("retMsg");
                    if ("success".equals(retMsg)) {
                        JSONObject retData = json.getJSONObject("retData");
                        IdentityInfo identityInfo = new IdentityInfo();
                        identityInfo.setSex(retData.getString("sex"));
                        identityInfo.setBirthday(retData.getString("birthday"));
                        identityInfo.setAddress(retData.getString("address"));
                        listener.onRequest(identityInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
