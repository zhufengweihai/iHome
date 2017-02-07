package com.zf.lottery.parse;

import android.support.annotation.NonNull;

import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;
import com.yolanda.nohttp.rest.SimpleResponseListener;
import com.zf.common.NoHttpUtils;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhufeng7 on 2016-6-29.
 */

public class LotteryJsRequest {
    public static final String LOTTERY_CLASS_NOTES = "notes";
    private static final String URL = "http://www.opencai.net/static/lottery.js";
    public static final String LOTTERY_TYPE_SEQ = "seq";
    public static final String LOTTERY_TYPE_DESCR = "descr";
    public static final String LOTTERY_CLASS_CODE = "code";
    public static final String LOTTERY_CLASS_TDESCR = "tdescr";
    public static final String LOTTERY_CLASS_DESCR = "descr";

    public void request(LotteryResolveListener listener) {
        Request request = NoHttp.createStringRequest(URL);
        NoHttpUtils.instance().requestQueue().add(NoHttpUtils.getWhat(), request, createResponseListener(listener));
    }

    @NonNull
    private SimpleResponseListener<String> createResponseListener(final LotteryResolveListener listener) {
        return new SimpleResponseListener<String>() {
            @Override
            public void onSucceed(int what, Response<String> response) {
                String js = response.get();
                String[] strings = StringUtils.substringsBetween(js, "=", ";");
                try {
                    JSONArray lotteries = new JSONArray(strings[0]);
                    JSONArray lotteryTypes = new JSONArray(strings[1]);

                    List<LotteryGroup> groups = parseLotteryClassGroups(lotteryTypes);
                    parseLotteryClasses(lotteries, groups);
                    Collections.sort(groups);
                    listener.onResolved(groups);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @NonNull
    private List<LotteryGroup> parseLotteryClassGroups(JSONArray lotteryTypes) throws JSONException {
        int length = lotteryTypes.length();
        List<LotteryGroup> groups = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            JSONObject lotteryType = lotteryTypes.getJSONObject(i);
            int seq = lotteryType.getInt(LOTTERY_TYPE_SEQ);
            String descr = lotteryType.getString(LOTTERY_TYPE_DESCR);
            LotteryGroup group = new LotteryGroup();
            group.setSequence(seq);
            group.setGroupName(descr);
            groups.add(group);
        }
        return groups;
    }

    private void parseLotteryClasses(JSONArray lotteries, List<LotteryGroup> groups) throws JSONException {
        int size = lotteries.length();
        for (int i = 0; i < size; i++) {
            JSONObject lottery = lotteries.getJSONObject(i);
            String code = lottery.getString(LOTTERY_CLASS_CODE);
            String tdescr = lottery.getString(LOTTERY_CLASS_TDESCR);
            String descr = lottery.getString(LOTTERY_CLASS_DESCR);
            String notes = lottery.getString(LOTTERY_CLASS_NOTES);
            Lottery lotteryClass = new Lottery();
            lotteryClass.setCode(code);
            lotteryClass.setName(descr);
            lotteryClass.setNotes(notes);

            classifiedIntoGroup(groups, tdescr, lotteryClass);
        }
    }

    private void classifiedIntoGroup(List<LotteryGroup> groups, String tdescr, Lottery lottery) {
        for (LotteryGroup group : groups) {
            if (group.getGroupName().equals(tdescr)) {
                group.addLottery(lottery);
                break;
            }
        }
    }
}
