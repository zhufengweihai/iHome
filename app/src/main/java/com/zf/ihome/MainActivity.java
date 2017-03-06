package com.zf.ihome;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.zf.common.widget.dragdrop.DragdropView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ArrayList<Pair<Integer, Integer>> appList = new ArrayList<>();
        Pair<Integer, Integer> pair = new Pair<>(R.mipmap.nav_lottery, R.string.nav_lottery);
        appList.add(pair);
        AppListAdapter adapter = new AppListAdapter(appList);
        DragdropView appListView = (DragdropView) findViewById(R.id.appListView);
        appListView.setAdapter(adapter);
    }
}
