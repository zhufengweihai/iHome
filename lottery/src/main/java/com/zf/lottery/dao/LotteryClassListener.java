package com.zf.lottery.dao;

import com.zf.lottery.data.LotteryClass;

import java.util.List;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public interface LotteryClassListener {
    void onRequest(List<LotteryClass> classes);
}
