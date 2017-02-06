package com.zf.fastshare.dao;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by zhufeng7 on 2016-10-12.
 */

public class DataHelper {
    public static final String NAME_PREFERENCES = "FAST_SHARE";
    public static final String FAST_SHARE_USER_NAME = "FAST_SHARE_USER_NAME";
    public static final String FAST_SHARE_SSID = "FAST_SHARE_SSID";
    public static final String FAST_SHARE_PASSWORD = "FAST_SHARE_PASSWORD";

    public void writeUserName(Context context, String userName) {
        SharedPreferences sp = context.getSharedPreferences(NAME_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(FAST_SHARE_USER_NAME, userName);
        editor.commit();
    }

    public String readUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME_PREFERENCES, MODE_PRIVATE);
        return sp.getString(FAST_SHARE_USER_NAME, null);
    }

    public void writeSSID(Context context, String ssid) {
        SharedPreferences sp = context.getSharedPreferences(NAME_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(FAST_SHARE_SSID, ssid);
        editor.commit();
    }

    public String readSSID(Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME_PREFERENCES, MODE_PRIVATE);
        return sp.getString(FAST_SHARE_SSID, null);
    }
}
