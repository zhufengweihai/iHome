package com.zf.lottery.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zf.lottery.R;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.service.LotteryStatService;
import com.zf.lottery.service.impl.SscStatServiceImpl;
import com.zf.lottery.view.help.DataHelper;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

public class SscStatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssc_stat);
        List<Lottery> lotteries = DataHelper.getInstance().retrieve();

        LineChartView chartView = (LineChartView) findViewById(R.id.chart);

        LineChartData data = createData(lotteries);
        chartView.setLineChartData(data);
        chartView.setZoomEnabled(true);
        chartView.setScrollEnabled(false);
        chartView.setZoomType(ZoomType.HORIZONTAL);
    }

    private LineChartData createData(List<Lottery> lotteries) {
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
}
