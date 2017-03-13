package com.zf.lottery.service.impl;

import com.zf.lottery.data.Lottery;
import com.zf.lottery.data.StatData;
import com.zf.lottery.data.TwoStarStatData;
import com.zf.lottery.service.LotteryStatService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class CqsscStatServiceImpl implements LotteryStatService {
    @Override
    public List<StatData> getNumberStat(List<Lottery> lotteries) {
        Map<Integer, TwoStarStatData> map = new HashMap<>();
        int size = lotteries.size();
        for (int i = 0; i < size; i++) {
            int[] numbers = lotteries.get(i).getNumbers();
            getStatData(map, numbers, i);
        }
        List<StatData> list = new ArrayList<>(map.size());
        list.addAll(map.values());
        return list;
    }

    @Override
    public int[] listMaxStat(List<Lottery> lotteries) {
        return new int[0];
    }

    private void getStatData(Map<Integer, TwoStarStatData> map, int[] numbers, int i) {
        int pair1 = numbers[3] * 10 + numbers[4];
        TwoStarStatData statData = map.get(pair1);
        if (statData == null) {
            int pair2 = numbers[4] * 10 + numbers[3];
            statData = map.get(pair2);
            if (statData == null) {
                statData = new TwoStarStatData();
                statData.setPair1(String.valueOf(pair1));
                statData.setNotOccurCount1(i);
                statData.setTotalNotOccurCount(i);
                map.put(pair1, statData);
            } else if (statData.getPair2() == null) {
                statData.setPair2(String.valueOf(pair1));
                statData.setNotOccurCount2(i);
                statData.setTotalNotOccurCount(statData.getNotOccurCount1() + i);
            }
        }
    }
}
