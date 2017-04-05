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
    public List<GroupAbsence> calcGroupAbence(Lottery lottery) {
        List<GroupAbsence> groupAbsences = new ArrayList<>();
        List<Absence> absences = lottery.getAbsences();
        for (int i = 0; i < 10; i++) {
            for (int j = i; j < 10; j++) {
                Absence absence1 = absences.get(i * 10 + j);
                if (i != j) {
                    Absence absence2 = absences.get(j * 10 + i);
                    GroupAbsence groupAbsence = new GroupAbsence(absence1, absence2);
                    groupAbsences.add(groupAbsence);
                } else {
                    GroupAbsence groupAbsence = new GroupAbsence(absence1);
                    groupAbsences.add(groupAbsence);
                }
            }
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

    public int[] calcGroupMaxAbence(List<Lottery> lotteries) {
        int[] max = new int[lotteries.size()];
        for (int i = 0; i < max.length; i++) {
            max[i] = Collections.max(calcGroupAbence(lotteries.get(i))).getGroupAbsence();
        }
        return max;
    }
}
