package com.zf.lottery.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.zf.common.app.BaseActivity;
import com.zf.lottery.R;
import com.zf.lottery.data.Absence;
import com.zf.lottery.data.GroupAbsence;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.service.LotteryResultListener;
import com.zf.lottery.service.SscService;
import com.zf.lottery.service.SscStatService;
import com.zf.lottery.view.help.AbsenceAdapter;
import com.zf.lottery.view.help.DataHelper;

import java.util.Comparator;
import java.util.List;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.listeners.SwipeToRefreshListener;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SscActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private SortableTableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cqssc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sscToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("时时彩");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppCompatSpinner typeSpinner = (AppCompatSpinner) findViewById(R.id.typeSpinner);
        typeSpinner.setOnItemSelectedListener(this);
        typeSpinner.setSelection(-1);

        tableView = (SortableTableView) findViewById(R.id.absenceTable);
        tableView.setSwipeToRefreshEnabled(true);
        PermissionsDispatcher.requestDataWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    public void requestData() {
        tableView.setSwipeToRefreshListener(new SwipeToRefreshListener() {
            @Override
            public void onRefresh(final RefreshIndicator refreshIndicator) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        SscService sscService = new SscService(SscActivity.this, new LotteryResultListener() {
                            @Override
                            public void onRequest(List<Lottery> lotteries) {
                                DataHelper.getInstance().save(lotteries);
                                refreshIndicator.hide();
                                ((AppCompatSpinner) findViewById(R.id.typeSpinner)).setSelection(0);
                            }
                        });
                        sscService.requestLottery();
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

    private void updateData(String[] titles, Comparator comparator, TableDataAdapter adapter) {
        tableView.setColumnCount(titles.length);
        SimpleTableHeaderAdapter headAdapter = new SimpleTableHeaderAdapter(this, titles);
        headAdapter.setTypeface(Typeface.NORMAL);
        headAdapter.setTextSize(12);
        headAdapter.setTextColor(getResources().getColor(R.color.textColorDark));
        tableView.setHeaderAdapter(headAdapter);
        tableView.setHeaderBackground(R.color.colorHead);
        tableView.setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());
        for (int i = 0; i < titles.length; i++) {
            tableView.setColumnComparator(i, null);
        }
        tableView.setColumnComparator(titles.length - 1, comparator);
        tableView.setDataAdapter(adapter);
        tableView.sort(titles.length - 1, false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (DataHelper.getInstance().retrieve() == null) {
            return;
        }
        if (position == 0) {
            String[] titles = {"号对1", "未出数", "号对2", "未出数", "未出和"};
            SscStatService sscStatService = new SscStatService();
            Lottery lottery = DataHelper.getInstance().retrieve().get(0);
            List<GroupAbsence> groupAbsences = sscStatService.calcGroupAbence(lottery);
            GroupAbsenceAdapter dataAdapter = new GroupAbsenceAdapter(SscActivity.this, groupAbsences);
            updateData(titles, new GroupAbsence.GroupAbsenceComparator(), dataAdapter);
        } else if (position == 1) {
            String[] titles = {"数字", "未出期数"};
            Lottery lottery = DataHelper.getInstance().retrieve().get(0);
            AbsenceAdapter dataAdapter = new AbsenceAdapter(SscActivity.this, lottery.getAbsences());
            updateData(titles, new Absence.AbsenceComparator(), dataAdapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
