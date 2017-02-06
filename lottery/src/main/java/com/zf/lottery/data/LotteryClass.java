package com.zf.lottery.data;

/**
 * Created by Administrator on 2017/2/6 0006.
 */

public class LotteryClass {
    private String name = null;
    private String code = null;
    private String notes = null;
    private String type = null;

    public LotteryClass() {
    }

    public LotteryClass(String name, String code, String notes, String type) {
        this.name = name;
        this.code = code;
        this.notes = notes;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
