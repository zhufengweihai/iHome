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
    public List<String> sortNumberOccurrences(List<Lottery> lotteries) {
        Map<String, Integer> map = new HashMap<>();
        int size = lotteries.size();
        for (int i = 0; i < size; i++) {
            String[] numbers = lotteries.get(i).getNumbers();
            String lastTwo = numbers[3] + numbers[4];
            Integer occurrs = map.get(lastTwo);
            if (occurrs == null) {
                map.put(lastTwo, i);
            }
        }
        return null;
    }
}
