package com.zf.lottery.data;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class TowStarStatData {
    private String towStart1 = null;
    private String towStart2 = null;
    private int notOccurCount1 = 0;
    private int notOccurCount2 = 0;
    private int totalNotOccurCount = 0;

    public String getTowStart1() {
        return towStart1;
    }

    public void setTowStart1(String towStart1) {
        this.towStart1 = towStart1;
    }

    public String getTowStart2() {
        return towStart2;
    }

    public void setTowStart2(String towStart2) {
        this.towStart2 = towStart2;
    }

    public int getNotOccurCount1() {
        return notOccurCount1;
    }

    public void setNotOccurCount1(int notOccurCount1) {
        this.notOccurCount1 = notOccurCount1;
    }

    public int getNotOccurCount2() {
        return notOccurCount2;
    }

    public void setNotOccurCount2(int notOccurCount2) {
        this.notOccurCount2 = notOccurCount2;
    }

    public int getTotalNotOccurCount() {
        return totalNotOccurCount;
    }

    public void setTotalNotOccurCount(int totalNotOccurCount) {
        this.totalNotOccurCount = totalNotOccurCount;
    }
}
