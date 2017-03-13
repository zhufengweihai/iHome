package com.zf.lottery.service.impl;

import com.zf.lottery.data.Lottery;
import com.zf.lottery.data.StatData;
import com.zf.lottery.service.LotteryStatService;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class SscStatServiceImpl implements LotteryStatService {
    public static final int MAX_NUM = 100;

    @Override
    public List<StatData> getNumberStat(List<Lottery> lotteries) {
        return null;
    }

    @Override
    public int[] listMaxStat(List<Lottery> lotteries) {
        int[] notOCcurs = new int[lotteries.size()];
        int[] occurArray = new int[MAX_NUM];
        int l = notOCcurs.length - 1;
        for (int i = l; i >= 0; i--) {
            Lottery lottery = lotteries.get(i);
            for (int j = 0; j < MAX_NUM; j++) {
                occurArray[j]++;
            }
            occurArray[lottery.getSum()] = 0;
            notOCcurs[l - i] = max(occurArray);
        }
        return notOCcurs;
    }

    private int max(int[] input) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < input.length; i++) {
            if (max < input[i]) {
                max = input[i];
            }
        }
        return max;
    }
}
