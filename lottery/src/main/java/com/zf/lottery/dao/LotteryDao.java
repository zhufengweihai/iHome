package com.zf.lottery.dao;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public interface LotteryDao {
    //void requestLotteryClass(LotteryClassListener listener);
    void obtainLotteryResults(LotteryResultsListener listener);
}
