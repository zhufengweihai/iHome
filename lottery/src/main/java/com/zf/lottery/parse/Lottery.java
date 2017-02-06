package com.zf.lottery.parse;

import com.zf.lottery.LotteryTerm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhufeng7 on 2016-6-29.
 */

public class Lottery {
    private String code = null;
    private String name = null;
    private String notes = null;
    private List<LotteryTerm> terms = new ArrayList<>(5);

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<LotteryTerm> getTerms() {
        return terms;
    }

    public LotteryTerm getTerm(int index) {
        return terms.get(index);
    }

    public void addTerm(LotteryTerm term) {
        this.terms.add(term);
    }

    public boolean isEmpty() {
        return terms.isEmpty();
    }
}
