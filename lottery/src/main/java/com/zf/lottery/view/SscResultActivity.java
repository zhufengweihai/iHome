package com.zf.lottery.view;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zf.lottery.R;
import com.zf.lottery.view.help.DataHelper;
import com.zf.lottery.view.help.SscResultAdapter;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class SscResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssc_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sscToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("时时彩");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TableView tableView = (TableView) findViewById(R.id.resultTable);
        String[] titles = {"时间", "期次", "号码"};
        SimpleTableHeaderAdapter headAdapter = new SimpleTableHeaderAdapter(this, titles);
        headAdapter.setTypeface(Typeface.NORMAL);
        headAdapter.setTextSize(15);
        headAdapter.setTextColor(getResources().getColor(R.color.textColorDark));
        tableView.setHeaderAdapter(headAdapter);
        tableView.setHeaderBackground(R.color.colorHead);
        tableView.setDataAdapter(new SscResultAdapter(this, DataHelper.getInstance().retrieve()));
    }
}
