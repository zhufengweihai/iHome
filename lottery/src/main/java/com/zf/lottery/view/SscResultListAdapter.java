package com.zf.lottery.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zf.lottery.R;
import com.zf.lottery.data.Lottery;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Administrator on 2017/2/13 0013.
 */

public class SscResultListAdapter extends RecyclerView.Adapter<SscResultListAdapter.ItemViewHolder> {
    private List<Lottery> lotteries = null;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public SscResultListAdapter(List<Lottery> lotteries) {
        this.lotteries = lotteries;
    }

    @Override
    public SscResultListAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ItemViewHolder(inflater.inflate(R.layout.item_ssc, parent, false));
    }

    @Override
    public void onBindViewHolder(SscResultListAdapter.ItemViewHolder holder, int position) {
        Lottery lottery = (Lottery) lotteries.get(position);
        holder.timeView.setText(dateFormat.format(lottery.getTime()));
        holder.termView.setText(lottery.getTerm().substring(8, 11));
        holder.numbersView.setText(getNumberString(lottery.getNumbers()));
    }

    private String getNumberString(int[] numbers) {
        int iMax = numbers.length - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; ; i++) {
            sb.append(numbers[i]);
            if (i == iMax) return sb.toString();
            sb.append("     ");
        }
    }

    @Override
    public int getItemCount() {
        return lotteries == null ? 0 : (lotteries.size() >= 50 ? 50 : lotteries.size());
    }

    protected static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final TextView timeView;
        public final TextView termView;
        public final TextView numbersView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            timeView = (TextView) itemView.findViewById(R.id.timeView);
            termView = (TextView) itemView.findViewById(R.id.termView);
            numbersView = (TextView) itemView.findViewById(R.id.numbersView);
        }
    }
}
