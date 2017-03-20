package com.zf.lottery.view;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zf.lottery.R;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.service.LotteryStatService;
import com.zf.lottery.service.impl.SscStatServiceImpl;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PreviewLineChartView;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class SscStatViewProxy {
    private View view = null;

    public SscStatViewProxy(LayoutInflater inflater, ViewGroup root, List<Lottery> lotteries) {
        view = inflater.inflate(R.layout.view_2_stat, root, false);
        LineChartView chartView = (LineChartView) view.findViewById(R.id.chart);
        PreviewLineChartView previewChart = (PreviewLineChartView) view.findViewById(R.id.chart_preview);

        LineChartData data = createata(lotteries);
        chartView.setLineChartData(data);
        chartView.setZoomEnabled(false);
        chartView.setScrollEnabled(false);

        LineChartData previewData = new LineChartData(data);
        previewData.getLines().get(0).setColor(ChartUtils.DEFAULT_DARKEN_COLOR);
        previewChart.setLineChartData(previewData);
        previewChart.setViewportChangeListener(new ViewportListener(chartView));

        previewX(chartView, previewChart, false);
    }

    public View getView() {
        return view;
    }

    private LineChartData createata(List<Lottery> lotteries) {
        LotteryStatService statService = new SscStatServiceImpl();
        int[] notOCcurs = statService.listMaxStat(lotteries);
        List<PointValue> values = new ArrayList<PointValue>();
        for (int i = 0; i < 10000; ++i) {
            values.add(new PointValue(i, notOCcurs[notOCcurs.length - 10000 + i]));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLOR_GREEN);
        line.setHasPoints(false);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData(lines);
        data.setAxisXBottom(new Axis());
        data.setAxisYLeft(new Axis().setHasLines(true));
        return data;
    }

    private void previewX(LineChartView chartView, PreviewLineChartView previewChart, boolean animate) {
        Viewport tempViewport = new Viewport(chartView.getMaximumViewport());
        float dx = tempViewport.width() / 4;
        tempViewport.inset(dx, 0);
        if (animate) {
            previewChart.setCurrentViewportWithAnimation(tempViewport);
        } else {
            previewChart.setCurrentViewport(tempViewport);
        }
        previewChart.setZoomType(ZoomType.HORIZONTAL);
    }

    private class ViewportListener implements ViewportChangeListener {
        LineChartView chartView = null;

        public ViewportListener(LineChartView chartView) {
            this.chartView = chartView;
        }

        @Override
        public void onViewportChanged(Viewport newViewport) {
            chartView.setCurrentViewport(newViewport);
        }

    }
}
