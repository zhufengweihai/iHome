package com.zf.lottery.dao;

import com.zf.lottery.data.Lottery;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public interface LotteryResultsListener {
    void onRequest(List<Lottery> lotteries);
}
