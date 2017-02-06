package com.zf.ihome;


import com.yolanda.nohttp.NoHttp;

/**
 * Created by zhufeng7 on 2016-6-29.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NoHttp.initialize(this);
//        Fresco.initialize(this);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Flyme-Light.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
    }
}
