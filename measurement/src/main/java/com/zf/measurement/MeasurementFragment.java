package com.zf.measurement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zf.measurement.view.AreaViewProxy;
import com.zf.measurement.view.LengthViewProxy;
import com.zf.measurement.view.MassViewProxy;
import com.zf.measurement.view.MeasurementViewProxy;
import com.zf.measurement.view.VolumeViewProxy;


public class MeasurementFragment extends Fragment {
    private static final String ARG_POSITION = "POSITION";

    public static MeasurementFragment newInstance(int positon) {
        MeasurementFragment fragment = new MeasurementFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, positon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int positon = getArguments().getInt(ARG_POSITION);
        MeasurementViewProxy proxy = null;
        if (positon == 0) {
            proxy = new LengthViewProxy(inflater, container);
        } else if (positon == 1) {
            proxy = new AreaViewProxy(inflater, container);
        } else if (positon == 2) {
            proxy = new VolumeViewProxy(inflater, container);
        } else if (positon == 3) {
            proxy = new MassViewProxy(inflater, container);
        } else {
            proxy = new LengthViewProxy(inflater, container);
        }

        return proxy.getView();
    }


    public static class MeasurementPagerAdapter extends FragmentPagerAdapter {
        private String[] titles = null;

        public MeasurementPagerAdapter(FragmentManager fm, String[] titles) {
            super(fm);
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return MeasurementFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}