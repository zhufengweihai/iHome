package com.zf.lottery.parse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhufeng7 on 2016-6-30.
 */

public class LotteryGroup implements Serializable, Comparable<LotteryGroup> {
    private int sequence = 0;
    private String groupName = null;
    private List<Lottery> lotteries = new ArrayList<>();

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Lottery> getLotteries() {
        return lotteries;
    }

    public Lottery getLottery(int index) {
        return lotteries.get(index);
    }

    public void addLottery(Lottery lottery) {
        lotteries.add(lottery);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LotteryGroup that = (LotteryGroup) o;

        return sequence == that.sequence;

    }

    @Override
    public int hashCode() {
        return sequence;
    }

    @Override
    public int compareTo(LotteryGroup another) {
        return sequence - another.sequence;
    }
}
