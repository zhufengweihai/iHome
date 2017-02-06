package com.zf.translation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/6 0006.
 */

public class TranslateService {
    @NonNull
    private SimpleResponseListener<String> createTkkResponseListener() {
        //        Request request = NoHttp.createStringRequest("https://translate.google.cn/m/translate");
//        RequestQueue requestQueue = NoHttpUtils.instance().getRequestQueue();
//        requestQueue.add(NoHttpUtils.getWhat(), request, createTkkResponseListener());
        return new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String s = response.get();
                s = StringUtils.substringBetween(s, "tkk:'", "'");
                String a = StringUtils.substringBetween(s, "a\\x3d", ";");
                String b = StringUtils.substringBetween(s, "b\\x3d", ";");
                String c = StringUtils.substringBetween(s, "return ", "+");
                String result = c + '.' + (Long.parseLong(a) + Long.parseLong(b));
                //translate(result);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long
                    networkMillis) {

            }
        };
    }

    public void translate(String sourceText, final TranslateResultListener listener) {
        String tk = tk(sourceText, TKK());
        String url = "http://translate.google.cn/translate_a/t?client=webapp&sl=zh-CN&tl=en&hl=zh-CN&dt=bd" +
                "&dt=ex&dt=ld&dt=md&dt=qca&dt=rw&dt=rm&dt=ss&dt=t&dt=at&ie=UTF-8&oe=UTF-8&otf=2&ssel=0" +
                "&tsel=0&kc=1&tk=" + tk + "&q=" + sourceText;

        Request request = NoHttp.createStringRequest(url);
        RequestQueue requestQueue = NoHttpUtils.instance().getRequestQueue();
        requestQueue.add(NoHttpUtils.getWhat(), request, new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String result = response.get();
                listener.onResult(result);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long
                    networkMillis) {
                Log.e("TranslateActivity", exception.getLocalizedMessage());
            }
        });
    }

    private String TKK() {
        long a = 561666268;
        long b = 1526272306;
        return 406398 + "." + (a + b);
    }

    private String tk(String a, String TKK) {
        String[] TKKs = TKK.split("\\.");
        String b = TKKs[0];
        List<Long> d = new ArrayList<>();
        for (int f = 0; f < a.length(); f++) {
            long g = a.charAt(f);
            if (128 > g) {
                d.add(g);
            } else {
                if (2048 > g) {
                    d.add(g >> 6 | 192);
                } else {
                    if (55296 == (g & 64512) && f + 1 < a.length() && 56320 == (a.charAt(f + 1) & 64512)) {
                        g = 65536 + ((g & 1023) << 10) + (a.charAt(++f) & 1023);
                        d.add(g >> 18 | 240);
                        d.add(g >> 12 & 63 | 128);
                    } else {
                        d.add(g >> 12 | 224);
                        d.add(g >> 6 & 63 | 128);
                    }
                }
                d.add(g & 63 | 128);
            }
        }

        long lb = Long.parseLong(b);
        long la = lb;
        for (Long l : d) {
            la += l;
            la = RL(la, "+-a^+6");
        }
        la = RL(la, "+-3^+b+-f");
        la ^= Long.parseLong(TKKs[1]);
        if (0 > la) {
            la = (la & 2147483647) + 2147483648l;
        }
        la = la % (long) Math.pow(10, 6);
        return la + "." + (la ^ lb);
    }

    private long RL(long a, String b) {
        for (int c = 0; c < b.length() - 2; c += 3) {
            char d = b.charAt(c + 2);
            long ld = d >= 'a' ? d - 87 : CharUtils.toIntValue(d);
            ld = b.charAt(c + 1) == '+' ? a >>> ld : a << ld;
            a = b.charAt(c) == '+' ? (a + ld & 4294967295l) : a ^ ld;
        }
        return a;
    }
}
