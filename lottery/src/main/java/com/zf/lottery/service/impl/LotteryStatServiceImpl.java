package com.zf.lottery.service.impl;

import android.util.Pair;

import com.zf.lottery.data.Lottery;
import com.zf.lottery.data.StatData;
import com.zf.lottery.service.LotteryStatService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class LotteryStatServiceImpl implements LotteryStatService {
    @Override
    public List<StatData> getNumberStat(List<Lottery> lotteries) {
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
        List<Pair<String, Integer>> list = new ArrayList<>(map.size());
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            list.add(new Pair<String, Integer>(entry.getKey(), entry.getValue()));
        }
        Collections.sort(list, new PairComparator());
        return null;
    }

    private class PairComparator implements Comparator<Pair<String, Integer>> {

        @Override
        public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
            if (o1.second > o2.second) {
                return 1;
            } else if (o1.second < o2.second) {
                return -1;
            } else {
                return o1.first.compareTo(o2.first);
            }
        }
    }

}
