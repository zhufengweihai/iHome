package com.zf.ihome;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        ArrayList<Pair<Integer, Integer>> appList = new ArrayList<>();
        Pair<Integer, Integer> pair = new Pair<>(R.mipmap.nav_lottery, R.string.nav_lottery);
        appList.add(pair);
        AppListAdapter adapter = new AppListAdapter(appList);
        RecyclerView appListView = (RecyclerView) findViewById(R.id.appListView);
        appListView.setAdapter(adapter);
        appListView.setLayoutManager(new LinearLayoutManager(this));
        appListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
}
