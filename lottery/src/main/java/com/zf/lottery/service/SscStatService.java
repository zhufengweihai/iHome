package com.zf.lottery.service;

import com.zf.lottery.data.Absence;
import com.zf.lottery.data.GroupAbsence;
import com.zf.lottery.data.Lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public class SscStatService {
    public List<GroupAbsence> calc2Abence(Lottery lottery) {
        List<GroupAbsence> groupAbsences = new ArrayList<>();
        List<Absence> absences = lottery.getAbsences();
        for (int i = 0; i < absences.size(); i++) {
            GroupAbsence groupAbsence = new GroupAbsence();
            Absence absence = absences.get(i);
            groupAbsence.addAbence(absence);
            groupAbsence.addAbence(absences.get(absence.getN2() * 10 + absence.getN1()));
            groupAbsences.add(groupAbsence);
        }
        return groupAbsences;
    }

    public int[] calcMaxAbence(List<Lottery> lotteries) {
        int[] max = new int[lotteries.size()];
        for (int i = 0; i < max.length; i++) {
            max[i] = Collections.max(lotteries.get(i).getAbsences()).getAbsence();
        }
        return max;
    }
}
