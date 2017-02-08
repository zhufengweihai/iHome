package com.zf.lottery.service.impl;

import com.zf.lottery.data.Lottery;
import com.zf.lottery.service.LotteryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class LotteryServiceImpl implements LotteryService {
    @Override
    public List<String> sortNumberSccurrences(List<Lottery> lotteries) {
        Map<String, String> map = new HashMap<>();
        for (Lottery lottery : lotteries) {
            String numbers = lottery.getNumbers();
        }
        return null;
    }
}
