package com.zf.lottery.data;

/**
 * Created by zhufeng7 on 2017-4-1.
 */

public class Abence {
    private int num1 = 0;
    private int absence1 = 0;
    private int num2 = 0;
    private int absence2 = 0;

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getAbsence1() {
        return absence1;
    }

    public void setAbsence1(int absence1) {
        this.absence1 = absence1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getAbsence2() {
        return absence2;
    }

    public void setAbsence2(int absence2) {
        this.absence2 = absence2;
    }

    public int getTotalAbence() {
        return absence1 + absence2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Abence that = (Abence) o;

        return num1 == that.num1 || num1 == that.num2;
    }

    @Override
    public int hashCode() {
        return num1 + num2;
    }
}
