package com.zf.lottery.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;

import com.zf.lottery.R;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.service.SscStatService;
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

public class SscStatActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssc_stat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sscToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("统计");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppCompatSpinner typeSpinner = (AppCompatSpinner) findViewById(R.id.typeSpinner);
        typeSpinner.setOnItemSelectedListener(this);
        typeSpinner.setSelection(0);

//        AppCompatSpinner numberSpinner = (AppCompatSpinner) findViewById(R.id.numberSpinner);
//        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,aaa);
//        numberSpinner.setAdapter(adapter);
    }

    private void initCharView(int[] maxAbences) {
        LineChartView chartView = (LineChartView) findViewById(R.id.chartView);
        List<Line> lines = new ArrayList<>();
        List<PointValue> values = new ArrayList<>();
        for (int i = 0; i < maxAbences.length; i++) {
            values.add(new PointValue(i, maxAbences[i]));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        List<Lottery> lotteries = DataHelper.getInstance().retrieve();
        SscStatService sscStatService = new SscStatService();
        if (position == 0) {
            int[] maxAbences = sscStatService.calcMaxAbence(lotteries);
            initCharView(maxAbences);
        } else if (position == 1) {
            int[] maxAbences = sscStatService.calcGroupMaxAbence(lotteries);
            initCharView(maxAbences);
        } else if (position == 2) {
            int[] maxAbences = sscStatService.calcDoubleNumberMaxAbence(lotteries);
            initCharView(maxAbences);
        } else if (position == 3) {
            int[] maxAbences = sscStatService.calc2NumberNextAbence(lotteries);
            initCharView(maxAbences);
        } else if (position == 4) {
            int[] maxAbences = sscStatService.calc2Number500NextAbence(lotteries);
            initCharView(maxAbences);
        } else if (position == 5) {
            int[] maxAbences = sscStatService.calc2TwinsNumberNextAbence(lotteries);
            initCharView(maxAbences);
        } else if (position == 6) {
            int[] maxAbences = sscStatService.calc2Twins300NextAbence(lotteries);
            initCharView(maxAbences);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
