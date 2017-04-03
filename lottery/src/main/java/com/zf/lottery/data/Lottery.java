package com.zf.lottery.data;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class Lottery implements Serializable {
    private long term = -1;
    private int[] numbers = null;
    private Date time = null;
    private int sum = -1;
    private int maxAbence = -1;
    private int[] absences = null;
    private int maxAbences = -1;

    public long getTerm() {
        return term;
    }

    public void setTerm(long term) {
        this.term = term;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getMaxAbence() {
        return maxAbence;
    }

    public void setMaxAbence(int maxAbence) {
        this.maxAbence = maxAbence;
    }

    public int[] getAbsences() {
        return absences;
    }

    public void setAbsences(int[] absences) {
        this.absences = absences;
    }

    public int getMaxAbences() {
        return maxAbences;
    }

    public void setMaxAbences(int maxAbences) {
        this.maxAbences = maxAbences;
    }

    public static class LotteryComparator implements Comparator<Lottery> {

        @Override
        public int compare(Lottery o1, Lottery o2) {
            if (o1.getMaxAbence() < o2.getMaxAbence()) return -1;
            if (o1.getMaxAbence() > o2.getMaxAbence()) return 1;
            return 0;
        }
    }
}
