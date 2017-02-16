package com.zf.lottery.service;

import android.util.Pair;

import com.zf.lottery.data.Lottery;
import com.zf.lottery.data.StatData;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public interface LotteryStatService {
    List<StatData> getNumberStat(List<Lottery> lotteries);
}
