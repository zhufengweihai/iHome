package com.zf.lottery.data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class Lottery implements Serializable {
    private int term = -1;
    private int[] numbers = null;
    private Timestamp time = null;

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
