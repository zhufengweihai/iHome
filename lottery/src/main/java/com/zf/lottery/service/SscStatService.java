package com.zf.lottery.service;

import com.zf.lottery.data.Absence;
import com.zf.lottery.data.GroupAbsence;
import com.zf.lottery.data.Lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

    public int[] calcDoubleNumberMaxAbence(List<Lottery> lotteries) {
        int[] max = new int[lotteries.size()];
        for (int i = 0; i < max.length; i++) {
            max[i] = Collections.max(lotteries.get(i).getAbsences(), new Absence.DoubleNumberComparator()).getAbsence();
        }
        return max;
    }

    public int[] calc2NumberNextAbence(List<Lottery> lotteries) {
        int[] nexts = new int[lotteries.size()];
        for (int i = 0; i < nexts.length; i++) {
            Absence[] absences = top2(lotteries.get(i).getAbsences());
            for (int j = i + 1; j < nexts.length; j++) {
                int[] numbers = lotteries.get(j).getNumbers();
                if ((absences[0].getN1() == numbers[3] && absences[0].getN2() == numbers[4]) || (absences[1].getN1()
                        == numbers[3] && absences[1].getN2() == numbers[4])) {
                    break;
                }
                nexts[i]++;
            }
        }
        return nexts;
    }

    private Absence[] top2(List<Absence> absences) {
        Iterator<Absence> i = absences.iterator();
        Absence[] candidates = new Absence[]{i.next(), i.next()};

        while (i.hasNext()) {
            Absence next = i.next();
            if (next.compareTo(candidates[0]) > 0) candidates[0] = next;
            else if (next.compareTo(candidates[1]) > 0) candidates[1] = next;
        }
        return candidates;
    }
}
