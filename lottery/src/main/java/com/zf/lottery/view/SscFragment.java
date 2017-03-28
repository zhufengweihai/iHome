package com.zf.lottery.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zf.lottery.R;
import com.zf.lottery.data.Lottery;

import java.io.Serializable;
import java.util.List;

public class SscFragment extends Fragment {
    private static final String ARG_POSITION = "POSITION";
    private static final String ARG_LOTTERY = "LOTTERY";

    public static SscFragment newInstance(int positon, List<Lottery> lotteries) {
        SscFragment fragment = new SscFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, positon);
        args.putSerializable(ARG_LOTTERY, (Serializable) lotteries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int positon = getArguments().getInt(ARG_POSITION);
        List<Lottery> lotteries = (List<Lottery>) getArguments().getSerializable(ARG_LOTTERY);
        if (positon == 0) {
            return new TwoStarSortViewProxy(inflater, container, lotteries).getView();
        } else if (positon == 1) {
            return new SscResultViewProxy(inflater, container, lotteries).getView();
        } else if (positon == 2) {
            // return new SscStatViewProxy(inflater, container, lotteries).getView();
        } else if (positon == 3) {

        } else {

        }

        return inflater.inflate(R.layout.view_2_sort, container, false);
    }

    public static class SscPagerAdapter extends FragmentPagerAdapter {
        private String[] titles = null;
        private List<Lottery> lotteries = null;
        private Fragment[] fragments = null;

        public SscPagerAdapter(FragmentManager fm, String[] titles, List<Lottery> lotteries) {
            super(fm);
            this.titles = titles;
            this.lotteries = lotteries;
            fragments = new Fragment[getCount()];
        }

        @Override
        public Fragment getItem(int position) {
            SscFragment fragment = SscFragment.newInstance(position, lotteries);
            fragments[position] = fragment;
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        public void updateData() {
            for (Fragment fragment : fragments) {
            }
        }
    }
}