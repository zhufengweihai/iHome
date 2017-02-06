package com.zf.measurement;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager.LayoutParams;

import com.zf.common.app.BaseActivity;
import com.zf.common.widget.PagerSlidingTabStrip;

import static com.zf.measurement.MeasurementFragment.MeasurementPagerAdapter;

public class MeasurementActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        getWindow().setFlags(LayoutParams.FLAG_ALT_FOCUSABLE_IM, LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        String[] titles = getResources().getStringArray(R.array.measurement);
        MeasurementPagerAdapter pagerAdapter = new MeasurementPagerAdapter(getSupportFragmentManager(), titles);
        viewPager.setAdapter(pagerAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.pagertab);
        tabs.setViewPager(viewPager);
    }
}
