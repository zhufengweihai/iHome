package com.zf.lottery.service;

import com.zf.lottery.data.StatData;
import com.zf.lottery.data.TwoStarStatData;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/2/19 0019.
 */

public class TowStarStatDataComparator implements Comparator<StatData> {
    public static final int POSITION_COMPARE_PAIR1 = 0;
    public static final int POSITION_COMPARE_ABSENCE1 = 1;
    public static final int POSITION_COMPARE_PAIR2 = 2;
    public static final int POSITION_COMPARE_ABSENCE2 = 3;
    public static final int POSITION_COMPARE_TOTAL_ABSENCE = 4;
    private boolean isAscending = false;
    private int positionCompare = POSITION_COMPARE_TOTAL_ABSENCE;

    public TowStarStatDataComparator() {
    }

    public TowStarStatDataComparator(boolean isAscending, int positionCompare) {
        this.isAscending = isAscending;
        this.positionCompare = positionCompare;
    }

    @Override
    public int compare(StatData o1, StatData o2) {
        TwoStarStatData data1 = (TwoStarStatData) o1;
        TwoStarStatData data2 = (TwoStarStatData) o2;

        int compareTo = compare(data1, data2);
        return isAscending ? compareTo : -compareTo;
    }

    private int compare(TwoStarStatData data1, TwoStarStatData data2) {
        if (positionCompare == POSITION_COMPARE_PAIR1) {
            return data1.getPair1().compareTo(data2.getPair1());
        } else if (positionCompare == POSITION_COMPARE_ABSENCE1) {
            if (data1.getNotOccurCount1() > data2.getNotOccurCount1()) {
                return 1;
            } else if (data1.getNotOccurCount1() < data2.getNotOccurCount1()) {
                return -1;
            } else {
                return data1.getPair1().compareTo(data2.getPair1());
            }
        } else if (positionCompare == POSITION_COMPARE_PAIR2) {
            return data1.getPair2().compareTo(data2.getPair2());
        } else if (positionCompare == POSITION_COMPARE_ABSENCE2) {
            if (data1.getNotOccurCount2() > data2.getNotOccurCount2()) {
                return 1;
            } else if (data1.getNotOccurCount2() < data2.getNotOccurCount2()) {
                return -1;
            } else {
                return 0;
            }
        } else {
            if (data1.getTotalNotOccurCount() > data2.getTotalNotOccurCount()) {
                return 1;
            } else if (data1.getTotalNotOccurCount() < data2.getTotalNotOccurCount()) {
                return -1;
            } else {
                return data1.getPair1().compareTo(data2.getPair1());
            }
        }
    }
}
