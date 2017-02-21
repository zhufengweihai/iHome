package com.zf.lottery.data;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class Lottery implements Serializable{
    private String term = null;
    private String[] numbers = null;
    private String time = null;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String[] getNumbers() {
        return numbers;
    }

    public void setNumbers(String[] numbers) {
        this.numbers = numbers;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
