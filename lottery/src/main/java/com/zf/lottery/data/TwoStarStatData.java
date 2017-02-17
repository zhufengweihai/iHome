package com.zf.lottery.data;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017/2/14 0014.
 */

public class TwoStarStatData extends StatData {
    private String pair1 = null;
    private String pair2 = null;
    private int notOccurCount1 = 0;
    private int notOccurCount2 = 0;
    private int totalNotOccurCount = 0;

    public String getPair1() {
        return pair1;
    }

    public void setPair1(String pair1) {
        this.pair1 = pair1;
    }

    public String getPair2() {
        return pair2;
    }

    public void setPair2(String pair2) {
        this.pair2 = pair2;
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
