package com.zf.lottery.data;

import java.io.Serializable;
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
}
