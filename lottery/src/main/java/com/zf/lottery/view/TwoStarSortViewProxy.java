package com.zf.lottery.view;

import android.support.annotation.IdRes;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.zf.lottery.R;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.data.StatData;
import com.zf.lottery.data.TwoStarStatData;
import com.zf.lottery.service.LotteryStatService;
import com.zf.lottery.service.TowStarStatDataComparator;
import com.zf.lottery.service.impl.SscStatServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class TwoStarSortViewProxy {
    private View view = null;

    public TwoStarSortViewProxy(LayoutInflater inflater, ViewGroup root, List<Lottery> lotteries) {
        view = inflater.inflate(R.layout.view_2_sort, root, false);
        LotteryStatService lotteryStatService = new SscStatServiceImpl();
        final List<Integer>[] notOCcurArray = lotteryStatService.getNumberStat(lotteries);
        final List<StatData> statDatas = new ArrayList<>();
        for (int i = 0; i < notOCcurArray.length / 2; i++) {
            TwoStarStatData statData = new TwoStarStatData();
            statData.setPair1(String.valueOf(i));
            statData.setNotOccurCount1(notOCcurArray[i].get(0));
            int pair2 = (i % 10) * 10 + i / 10;
            if (pair2 != i) {
                statData.setPair2(String.valueOf(pair2));
                statData.setNotOccurCount1(notOCcurArray[pair2].get(0));
            }
            statData.setTotalNotOccurCount(statData.getNotOccurCount1() + statData.getNotOccurCount2());
        }
        Collections.sort(statDatas, new TowStarStatDataComparator());
        final TowStarStatListAdapter statListAdapter = new TowStarStatListAdapter(statDatas);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.occurListView);
        recyclerView.setAdapter(statListAdapter);
        RadioGroup titleGroup = (RadioGroup) view.findViewById(R.id.titleGroup);
        titleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (checkedId == group.getChildAt(i).getId()) {
                        int positionCompare = TowStarStatDataComparator.POSITION_COMPARE_TOTAL_ABSENCE;
                        if (i == 0) {
                            positionCompare = TowStarStatDataComparator.POSITION_COMPARE_PAIR1;
                        } else if (i == 1) {
                            positionCompare = TowStarStatDataComparator.POSITION_COMPARE_ABSENCE1;
                        } else if (i == 2) {
                            positionCompare = TowStarStatDataComparator.POSITION_COMPARE_PAIR2;
                        } else if (i == 3) {
                            positionCompare = TowStarStatDataComparator.POSITION_COMPARE_ABSENCE2;
                        } else if (i == 4) {
                            positionCompare = TowStarStatDataComparator.POSITION_COMPARE_TOTAL_ABSENCE;
                        }
                        Collections.sort(statDatas, new TowStarStatDataComparator(false, positionCompare));
                        statListAdapter.setStatDatas(statDatas);
                    }
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
    }

    public View getView() {
        return view;
    }
}
