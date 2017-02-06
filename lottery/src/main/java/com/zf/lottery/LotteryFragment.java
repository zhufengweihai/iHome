package com.zf.lottery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.zf.lottery.parse.LotteryGroup;

import java.util.List;

public class LotteryFragment extends Fragment {
    private static final String ARG_LOTTERY_CLASS = "lottery_class";

    public static LotteryFragment newInstance(int sectionNumber, LotteryGroup group) {
        LotteryFragment fragment = new LotteryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOTTERY_CLASS, group);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ExpandableListView view = (ExpandableListView) inflater.inflate(R.layout.list_lottery, container, false);
        LotteryGroup group = (LotteryGroup) getArguments().getSerializable(ARG_LOTTERY_CLASS);
        view.setAdapter(new LotteryItemAdapter(group));
        return view;
    }

    public static class LotteryPagerAdapter extends FragmentPagerAdapter {
        private List<LotteryGroup> groups = null;

        public LotteryPagerAdapter(FragmentManager fm, List<LotteryGroup> groups) {
            super(fm);
            this.groups = groups;
        }

        @Override
        public Fragment getItem(int position) {
            return LotteryFragment.newInstance(position + 1, groups.get(position));
        }

        @Override
        public int getCount() {
            return groups.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return groups.get(position).getGroupName();
        }
    }
}