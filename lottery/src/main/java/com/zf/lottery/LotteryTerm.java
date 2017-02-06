package com.zf.lottery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhufeng7 on 2016-6-27.
 */

public class LotteryTerm {
    private int term = 0;
    private List<String> redCodes = new ArrayList<>(6);
    private List<String> blueCodes = new ArrayList<>(2);
    private String openTime = null;

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public List<String> getRedCodes() {
        return redCodes;
    }

    public void addRedCode(String... redCodes) {
        for (String redCode : redCodes) {
            this.redCodes.add(redCode);
        }
    }

    public String redCodesToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < redCodes.size() - 1; i++) {
            builder.append(redCodes.get(i)).append(' ');
        }

        return builder.toString();
    }

    public List<String> getBlueCodes() {
        return blueCodes;
    }

    public void addBlueCode(String... blueCodes) {
        for (String blueCode : blueCodes) {
            this.blueCodes.add(blueCode);
        }
    }

    public String blueCodesToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < blueCodes.size() - 1; i++) {
            builder.append(blueCodes.get(i)).append(' ');
        }

        return builder.toString();
    }

    public String getOpenTime() {
        return openTime;
    }


    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }
}
