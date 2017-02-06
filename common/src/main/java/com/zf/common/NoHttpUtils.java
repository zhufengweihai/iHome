package com.zf.common;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.RequestQueue;

/**
 * Created by zhufeng7 on 2016-6-27.
 */

public class NoHttpUtils {
    private static final int Mess = 766;
    private static NoHttpUtils noHttpUtils = null;
    private static int WHAT = 0;

    private RequestQueue requestQueue = null;

    private NoHttpUtils() {
        requestQueue = NoHttp.newRequestQueue(5);
    }

    public static NoHttpUtils instance() {
        if (noHttpUtils == null) {
            noHttpUtils = new NoHttpUtils();
        }
        return noHttpUtils;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public static int getWhat() {
        if (WHAT > Integer.MAX_VALUE) {
            WHAT = 0;
        }
        return WHAT++;
    }
}