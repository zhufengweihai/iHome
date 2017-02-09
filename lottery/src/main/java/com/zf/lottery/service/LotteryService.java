package com.zf.lottery.service;

import com.zf.lottery.data.Lottery;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public interface LotteryService {
    List<String> sortNumberOccurrences(List<Lottery> lotteries);
}
