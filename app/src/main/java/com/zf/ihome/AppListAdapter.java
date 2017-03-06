package com.zf.ihome;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zf.common.widget.dragdrop.ItemTouchHelperAdapter;
import com.zf.common.widget.dragdrop.ItemTouchHelperViewHolder;
import com.zf.lottery.view.LotteriesActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ItemViewHolder>implements ItemTouchHelperAdapter {
    private List<Pair<Integer, Integer>> appList = null;

    public AppListAdapter(List<Pair<Integer, Integer>> appList) {
        this.appList = appList;
    }

    @Override
    public AppListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(inflater.inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(AppListAdapter.ItemViewHolder holder, int position) {
        Pair<Integer, Integer> app = appList.get(position);
        Drawable drawable = holder.itemView.getContext().getResources().getDrawable(app.first);
        String string = holder.itemView.getContext().getResources().getString(app.second);
        TextView itemView = (TextView) holder.itemView;
        itemView.setCompoundDrawables(drawable, null, null, null);
        itemView.setText(string);
        if (position % 2 == 1) {
            int color = holder.itemView.getContext().getResources().getColor(R.color.colorBackground);
            holder.itemView.setBackgroundColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return appList == null ? 0 : appList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {

    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onItemSelected() {
            itemView.getContext().startActivity(new Intent(itemView.getContext(), LotteriesActivity.class));
        }

        @Override
        public void onItemClear() {

        }
    }
}
