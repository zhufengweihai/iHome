package com.zf.lottery.data;

import android.support.annotation.NonNull;

/**
 * Created by zhufeng7 on 2017-4-1.
 */

public class Absence implements Comparable<Absence> {
    private int n1 = -1;
    private int n2 = -1;
    private int absence = 0;

    public Absence(Absence absence) {
        this.n1 = absence.n1;
        this.n2 = absence.n2;
        this.absence = absence.absence;
    }

    public Absence(int n1, int n2) {
        this.n1 = n1;
        this.n2 = n2;
    }

    public int getN1() {
        return n1;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public int getN2() {
        return n2;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public int getAbsence() {
        return absence;
    }

    public void setAbsence(int absence) {
        this.absence = absence;
    }

    public void increaseAbsence() {
        this.absence++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Absence absence = (Absence) o;

        if (n1 != absence.n1) return false;
        return n2 == absence.n2;

    }

    @Override
    public int hashCode() {
        int result = n1;
        result = 31 * result + n2;
        return result;
    }

    @Override
    public int compareTo(@NonNull Absence o) {
        if (absence > o.absence) {
            return 1;
        } else if (absence < o.absence) {
            return -1;
        }
        return 0;
    }
}
