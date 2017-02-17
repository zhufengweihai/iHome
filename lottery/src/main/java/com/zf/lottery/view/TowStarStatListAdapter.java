package com.zf.lottery.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zf.lottery.R;
import com.zf.lottery.data.StatData;
import com.zf.lottery.data.TwoStarStatData;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13 0013.
 */

public class TowStarStatListAdapter extends RecyclerView.Adapter<TowStarStatListAdapter.ItemViewHolder> {
    private List<StatData> statDatas = null;

    public TowStarStatListAdapter(List<StatData> statDatas) {
        this.statDatas = statDatas;
    }

    @Override

    public TowStarStatListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(inflater.inflate(R.layout.item_tow_star, parent, false));
    }

    @Override
    public void onBindViewHolder(TowStarStatListAdapter.ItemViewHolder holder, int position) {
        TwoStarStatData statData = (TwoStarStatData) statDatas.get(position);
        holder.towStart1View.setText(statData.getPair1());
        holder.notOccurCount1View.setText(String.valueOf(statData.getNotOccurCount1()));
        holder.towStart2View.setText(statData.getPair2());
        holder.notOccurCount2View.setText(String.valueOf(statData.getNotOccurCount2()));
        holder.totalNotOccurCountView.setText(String.valueOf(statData.getTotalNotOccurCount()));
    }

    @Override
    public int getItemCount() {
        return statDatas == null ? 0 : statDatas.size();
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView towStart1View;
        public final TextView notOccurCount1View;
        public final TextView towStart2View;
        public final TextView notOccurCount2View;
        public final TextView totalNotOccurCountView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            towStart1View = (TextView) itemView.findViewById(R.id.towStart1View);
            notOccurCount1View = (TextView) itemView.findViewById(R.id.occurCount1View);
            towStart2View = (TextView) itemView.findViewById(R.id.towStart2View);
            notOccurCount2View = (TextView) itemView.findViewById(R.id.occurCount2View);
            totalNotOccurCountView = (TextView) itemView.findViewById(R.id.totalOccurCountView);
        }
    }
}
