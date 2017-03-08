package com.zf.lottery.view;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zf.lottery.R;
import com.zf.lottery.data.Lottery;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class SscResultViewProxy {
    private View view = null;

    public SscResultViewProxy(LayoutInflater inflater, ViewGroup root, List<Lottery> lotteries) {
        view = inflater.inflate(R.layout.view_2_result, root, false);
        SscResultListAdapter statListAdapter = new SscResultListAdapter(lotteries);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.resultListView);
        recyclerView.setAdapter(statListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(root.getContext(), DividerItemDecoration.VERTICAL));
    }

    public View getView() {
        return view;
    }
}
