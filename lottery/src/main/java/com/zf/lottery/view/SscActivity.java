package com.zf.lottery.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zf.common.app.BaseActivity;
import com.zf.lottery.R;
import com.zf.lottery.data.GroupAbsence;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.service.LotteryResultListener;
import com.zf.lottery.service.SscService;
import com.zf.lottery.service.SscStatService;
import com.zf.lottery.view.help.DataHelper;

import java.util.List;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.listeners.SwipeToRefreshListener;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SscActivity extends BaseActivity {
    private SortableTableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cqssc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sscToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("时时彩");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tableView = (SortableTableView) findViewById(R.id.absenceTable);
        String[] titles = {"号对1", "未出数", "号对2", "未出数", "未出和"};
        SimpleTableHeaderAdapter headAdapter = new SimpleTableHeaderAdapter(this, titles);
        headAdapter.setTypeface(Typeface.NORMAL);
        headAdapter.setTextSize(12);
        headAdapter.setTextColor(getResources().getColor(R.color.textColorDark));
        tableView.setHeaderAdapter(headAdapter);
        tableView.setHeaderBackground(R.color.colorHead);
        tableView.setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());
        tableView.setColumnComparator(4, new GroupAbsence.GroupAbsenceComparator());
        tableView.sort(4, false);
        PermissionsDispatcher.requestDataWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void requestData() {
        tableView.setSwipeToRefreshEnabled(true);
        tableView.setSwipeToRefreshListener(new SwipeToRefreshListener() {
            @Override
            public void onRefresh(final RefreshIndicator refreshIndicator) {
                tableView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SscService sscService = new SscService(SscActivity.this, new LotteryResultListener() {
                            @Override
                            public void onRequest(List<Lottery> lotteries) {
                                DataHelper.getInstance().save(lotteries);
                                SscStatService sscStatService = new SscStatService();
                                List<GroupAbsence> groupAbsences = sscStatService.calcGroupAbence(lotteries.get(0));
                                AbsenceDataAdapter dataAdapter = new AbsenceDataAdapter(SscActivity.this,
                                        groupAbsences);
                                tableView.setDataAdapter(dataAdapter);

                                refreshIndicator.hide();
                            }
                        });
                        sscService.requestLottery();
                    }
                }, 500);
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
        if (id == R.id.action_result) {
            startActivity(new Intent(this, SscResultActivity.class));
            return true;
        }
        if (id == R.id.action_stat) {
            startActivity(new Intent(this, SscStatActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
