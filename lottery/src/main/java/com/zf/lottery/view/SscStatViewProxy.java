package com.zf.lottery.view;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zf.lottery.R;
import com.zf.lottery.data.Lottery;

import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class SscStatViewProxy {
    private View view = null;

    public SscStatViewProxy(LayoutInflater inflater, ViewGroup root, List<Lottery> lotteries) {
        view = inflater.inflate(R.layout.view_2_stat, root, false);
        LineChartView chartView = (LineChartView) view.findViewById(R.id.chartView);
        chartView.setInteractive(true);
        chartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
    }

    public View getView() {
        return view;
    }
}
