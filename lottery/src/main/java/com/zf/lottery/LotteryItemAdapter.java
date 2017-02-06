package com.zf.lottery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zf.lottery.parse.Lottery;
import com.zf.lottery.parse.LotteryGroup;
import com.zf.lottery.parse.LotteryInfoRequest;
import com.zf.lottery.parse.LotteryInfoResolveListener;

/**
 * Created by zhufeng7 on 2016-6-27.
 */

public class LotteryItemAdapter extends BaseExpandableListAdapter {
    private LotteryGroup group = null;
    private int expandedGroupLayout;
    private int collapsedGroupLayout;
    private LotteryInfoRequest request = null;

    public LotteryItemAdapter(LotteryGroup group) {
        this.group = group;
        request = new LotteryInfoRequest();
        for (Lottery lottery : group.getLotteries()) {
            request.request(lottery, new LotteryInfoResolveListener() {
                @Override
                public void onResolved(Lottery lottery) {
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getGroupCount() {
        return group.getLotteries().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return group.getLottery(groupPosition).getTerms().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.getLottery(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return group.getLottery(groupPosition).getTerm(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = newGroupView(isExpanded, parent);
        } else {
            v = convertView;
        }
        bindView(v, group.getLottery(groupPosition), 0);
        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup
            parent) {
        View v;
        if (convertView == null) {
            v = newChildView(isLastChild, parent);
        } else {
            v = convertView;
        }
        bindView(v, group.getLottery(groupPosition), childPosition);
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private View newGroupView(boolean isExpanded, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.list_item_lottery_collapsed, parent, false);
    }

    private void bindView(View view, Lottery lottery, int term) {
        if (lottery.isEmpty()) {
            return;
        }
        TextView nameView = (TextView) view.findViewById(R.id.nameView);
        if (nameView != null) {
            nameView.setText(lottery.getName());
        }
        TextView phaseView = (TextView) view.findViewById(R.id.phaseView);
        phaseView.setText(view.getContext().getString(R.string.term, lottery.getTerm(term).getTerm()));
        TextView dateView = (TextView) view.findViewById(R.id.dateView);
        dateView.setText(lottery.getTerm(term).getOpenTime());
        TextView redNumberView = (TextView) view.findViewById(R.id.redNumberView);
        redNumberView.setText(lottery.getTerm(term).redCodesToString());
        TextView blueNumberView = (TextView) view.findViewById(R.id.blueNumberView);
        blueNumberView.setText(lottery.getTerm(term).blueCodesToString());
    }

    public View newChildView(boolean isLastChild, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.list_item_lottery_expanded, parent, false);
    }
}
