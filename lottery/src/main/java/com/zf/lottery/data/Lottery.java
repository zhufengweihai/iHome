package com.zf.lottery.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8 0008.
 */

public class Lottery implements Serializable {
    private long term = -1;
    private int[] numbers = null;
    private Date time = null;
    private int sum = -1;
    private List<Absence> absences = null;

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

    public List<Absence> getAbsences() {
        return absences;
    }

    public void setAbsences(List<Absence> absences) {
        this.absences = absences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lottery lottery = (Lottery) o;

        return term == lottery.term;

    }

    @Override
    public int hashCode() {
        return (int) (term ^ (term >>> 32));
    }

    @Override
    public String toString() {
        return "Lottery{" + "term=" + term + ", numbers=" + Arrays.toString(numbers) + ", time=" + time + '}';
    }
}
