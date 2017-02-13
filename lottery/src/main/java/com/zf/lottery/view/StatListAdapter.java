package com.zf.lottery.view;

import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zf.lottery.R;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */

public class StatListAdapter extends RecyclerView.Adapter<StatListAdapter.ItemViewHolder> {
    private List<Pair<String, Integer>> occurList = null;

    public StatListAdapter(List<Pair<String, Integer>> occurList) {
        this.occurList = occurList;
    }

    @Override

    public StatListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(inflater.inflate(R.layout.item_occurs, parent, false));
    }

    @Override
    public void onBindViewHolder(StatListAdapter.ItemViewHolder holder, int position) {
        holder.towStartView.setText(occurList.get(position).first);
        holder.occurCountView.setText(String.valueOf(occurList.get(position).second));
    }

    @Override
    public int getItemCount() {
        return occurList == null ? 0 : occurList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView towStartView;
        public final TextView occurCountView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            towStartView = (TextView) itemView.findViewById(R.id.towStartView);
            occurCountView = (TextView) itemView.findViewById(R.id.occurCountView);
        }
    }
}
