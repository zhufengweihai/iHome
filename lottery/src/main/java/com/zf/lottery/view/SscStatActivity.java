package com.zf.lottery.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zf.lottery.R;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.service.LotteryStatService;
import com.zf.lottery.service.SscStatService;
import com.zf.lottery.service.impl.SscStatServiceImpl;
import com.zf.lottery.view.help.DataHelper;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class SscStatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssc_stat);

        List<Lottery> lotteries = DataHelper.getInstance().retrieve();
        SscStatService sscStatService = new SscStatService();
        int[] maxAbences = sscStatService.calcMaxAbence(lotteries);
        LineChartView chartView = (LineChartView) findViewById(R.id.chart);
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();
        int l = maxAbences.length - 1;
        for (int i = l; i >= 0; i--) {
            values.add(new PointValue(l - i, maxAbences[i]));
        }

        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]);
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(false);
        line.setHasLabelsOnlyForSelected(false);
        line.setHasLines(true);
        line.setHasPoints(false);
        lines.add(line);


        LineChartData data = new LineChartData(lines);

        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        chartView.setLineChartData(data);
        chartView.setZoomType(ZoomType.HORIZONTAL);
    }

}
