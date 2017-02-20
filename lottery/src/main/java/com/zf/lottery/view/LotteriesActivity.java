package com.zf.lottery.view;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.zf.common.app.BaseActivity;
import com.zf.lottery.LotteryFragment;
import com.zf.lottery.R;
import com.zf.lottery.dao.LotteryClassDao;
import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.dao.impl.LotteryClassDaoImpl;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.data.StatData;
import com.zf.lottery.service.LotteryStatService;
import com.zf.lottery.service.TowStarStatDataComparator;
import com.zf.lottery.service.impl.CqsscStatServiceImpl;

import java.util.Collections;
import java.util.List;

public class LotteriesActivity extends BaseActivity implements LotteryResultsListener {
    private LotteryFragment.LotteryPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private List<StatData> statDatas;
    private TowStarStatListAdapter statListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotteries);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("二星复选");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        statListAdapter = new TowStarStatListAdapter(statDatas);
        ((RecyclerView) findViewById(R.id.occurListView)).setAdapter(statListAdapter);
        RadioGroup titleGroup = (RadioGroup) findViewById(R.id.titleGroup);
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
        ((RecyclerView) findViewById(R.id.occurListView)).setLayoutManager(new LinearLayoutManager(this));
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
        LotteryStatService lotteryStatService = new CqsscStatServiceImpl();
        statDatas = lotteryStatService.getNumberStat(lotteries);
        //Collections.sort(statDatas, new TowStarStatDataComparator());
        statListAdapter.setStatDatas(statDatas);
    }
}
