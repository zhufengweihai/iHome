package com.zf.lottery.dao.impl;

import android.os.Environment;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;
import com.zf.lottery.common.Commons;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class SscDaoLocalImpl implements LotteryDao {

    @Override
    public void obtainLotteryResults(LotteryResultsListener listener) {
        String historyPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/home/cqssc.txt";
        try {
            List<String> results = FileUtils.readLines(new File(historyPath), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
