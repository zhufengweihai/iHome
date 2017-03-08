package com.zf.lottery.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager.LayoutParams;

import com.zf.common.app.BaseActivity;
import com.zf.common.widget.PagerSlidingTabStrip;
import com.zf.lottery.R;
import com.zf.lottery.common.Commons;
import com.zf.lottery.data.Lottery;

import java.util.List;

public class SscActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssc);
        getWindow().setFlags(LayoutParams.FLAG_ALT_FOCUSABLE_IM, LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sscToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("时时彩");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startActivityForResult(new Intent(this, LotteryLoadingActivity.class), Commons.REQUEST_CODE_LOTTERY_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Lottery> lotteries = (List<Lottery>) data.getSerializableExtra(Commons.RETURN_VALUE_LOTTERY_RESULT);

        ViewPager viewPager = (ViewPager) findViewById(R.id.sscViewPager);
        String[] titles = getResources().getStringArray(R.array.ssc);
        SscFragment.SscPagerAdapter pagerAdapter = new SscFragment.SscPagerAdapter(getSupportFragmentManager(), titles,lotteries);
        viewPager.setAdapter(pagerAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.sscPagerTab);
        tabs.setViewPager(viewPager);
    }
}
