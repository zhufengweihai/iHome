package com.zf.lottery.view;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zf.common.app.BaseActivity;
import com.zf.common.widget.PagerSlidingTabStrip;
import com.zf.lottery.R;
import com.zf.lottery.dao.LotteryDao;
import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.dao.impl.SscDaoImpl;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.view.SscFragment.SscPagerAdapter;
import com.zf.lottery.view.help.DataHelper;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SscActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sscToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("时时彩");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PermissionsDispatcher.loadingWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void loading() {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        ;
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.textColor);
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        swipeRefreshLayout.setRefreshing(false);
                        ViewPager viewPager = (ViewPager) findViewById(R.id.sscViewPager);
                        String[] titles = getResources().getStringArray(R.array.ssc);
                        List<Lottery> lotteries = DataHelper.getInstance().retrieve();
                        SscPagerAdapter pagerAdapter = new SscPagerAdapter(getSupportFragmentManager(), titles, lotteries);
                        viewPager.setAdapter(pagerAdapter);
                        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.sscPagerTab);
                        tabs.setViewPager(viewPager);
                        break;
                    default:
                        break;
                }
            }
        };

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        LotteryDao lotteryDao = new SscDaoImpl(SscActivity.this);
                        lotteryDao.obtainLotteryResults(new LotteryResultsListener() {
                            @Override
                            public void onRequest(List<Lottery> lotteries) {
                                DataHelper.getInstance().save(lotteries);
                                handler.sendEmptyMessage(1);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lottery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_stat) {
            startActivity(new Intent(this, SscStatActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
