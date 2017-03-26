package com.zf.lottery.view.help;

import com.zf.lottery.data.Lottery;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2017/3/25 0025.
 */

public class DataHelper {
    private WeakReference<List<Lottery>> lotteries = null;
    private static DataHelper helper = new DataHelper();

    private DataHelper() {
    }

    public static DataHelper getInstance() {
        return helper;
    }

    public void save(List<Lottery> lotteries) {
        this.lotteries = new WeakReference<List<Lottery>>(lotteries);
    }

    public List<Lottery> retrieve() {
        return lotteries.get();
    }
}
