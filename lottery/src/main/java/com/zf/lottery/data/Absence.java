package com.zf.lottery.data;

import android.support.annotation.NonNull;

import java.util.Comparator;

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
    public String toString() {
        return "Absence{" + "n1=" + n1 + ", n2=" + n2 + ", absence=" + absence + '}';
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

    public static class AbsenceComparator implements Comparator<Absence> {

        @Override
        public int compare(Absence o1, Absence o2) {
            return o1.compareTo(o2);
        }
    }

    public static class DoubleNumberComparator implements Comparator<Absence> {

        @Override
        public int compare(Absence o1, Absence o2) {
            if (o1.n1 != o1.n2) {
                return -1;
            } else if (o2.n1 != o2.n2) {
                return 1;
            } else {
                return o1.compareTo(o2);
            }
        }
    }
}
