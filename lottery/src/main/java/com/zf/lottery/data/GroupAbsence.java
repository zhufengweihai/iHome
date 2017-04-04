package com.zf.lottery.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class GroupAbsence {
    private List<Absence> absences = new ArrayList<>();

    public void addAbence(Absence absence) {
        if (!absences.contains(absence)) {
            absences.add(absence);
        }
    }

    public Absence getAbsence(int i) {
        if (i < absences.size()) return absences.get(i);
        return null;
    }

    public int getGroupAbsence() {
        int i = 0;
        for (Absence absence : absences) {
            i += absence.getAbsence();
        }
        return i;
    }
}
