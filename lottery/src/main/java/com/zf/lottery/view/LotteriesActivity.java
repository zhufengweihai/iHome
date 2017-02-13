package com.zf.lottery.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import com.zf.common.app.BaseActivity;
import com.zf.lottery.LotteryFragment;
import com.zf.lottery.R;
import com.zf.lottery.dao.LotteryClassDao;
import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.dao.impl.LotteryClassDaoImpl;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.service.LotteryService;
import com.zf.lottery.service.impl.LotteryServiceImpl;

import java.util.List;

public class LotteriesActivity extends BaseActivity implements LotteryResultsListener {
    private LotteryFragment.LotteryPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotteries);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        LotteryClassDao lotteryClassDao = new LotteryClassDaoImpl();
        lotteryClassDao.requestLotteryResults(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_lottery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequest(List<Lottery> lotteries) {
        LotteryService lotteryService = new LotteryServiceImpl();
        List<Pair<String, Integer>> occurList = lotteryService.sortNumberOccurrences(lotteries);
        Log.e("LotteriesActivity", occurList.toString());
    }
}
