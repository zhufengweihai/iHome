package com.zf.lottery.data;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class GroupAbsence implements Comparable<GroupAbsence> {
    private Absence[] absences = null;

    public GroupAbsence(Absence... absences) {
        this.absences = absences;
    }

    public Absence getAbsence(int i) {
        if (i < absences.length) return absences[i];
        return null;
    }

    public int getGroupAbsence() {
        int i = 0;
        for (Absence absence : absences) {
            i += absence.getAbsence();
        }
        return i;
    }

    @Override
    public int compareTo(@NonNull GroupAbsence o) {
        int groupAbsence = getGroupAbsence();
        int groupAbsence1 = o.getGroupAbsence();
        if (groupAbsence > groupAbsence1) {
            return 1;
        } else if (groupAbsence < groupAbsence1) {
            return -1;
        }
        return 0;
    }


    public static class GroupAbsenceComparator implements Comparator<GroupAbsence> {

        @Override
        public int compare(GroupAbsence o1, GroupAbsence o2) {
            if (o1.getGroupAbsence() < o2.getGroupAbsence()) return -1;
            if (o1.getGroupAbsence() > o2.getGroupAbsence()) return 1;
            return 0;
        }
    }
}
