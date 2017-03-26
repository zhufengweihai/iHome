package com.zf.lottery.service;

import android.content.Context;

import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.dao.impl.LotteryDbHelper;
import com.zf.lottery.data.Lottery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/26 0026.
 */

public class SscService implements LotteryResultsListener {
    private static final int ONE_MINITE = 60 * 1000;
    private static final int MIN_SIZE = 1000;
    private static final int MAX_SIZE = 11000;
    private static final int SIZE = MAX_SIZE - MIN_SIZE;

    private LotteryDbHelper dbHelper = null;
    private List<Lottery> historyLotteries = null;
    private List<Lottery> lotteries = new ArrayList<>(MAX_SIZE);
    private SscRequestHandler handler = new SscRequestHandler();

    public SscService(Context context) {
        dbHelper = new LotteryDbHelper(context);
    }

    public void requestLottery() {
        historyLotteries = dbHelper.readSscResult();
        if (historyLotteries.size() < MIN_SIZE) {
            handler.requestLottery(new Date(), this);
        } else {

        }
    }

    @Override
    public void onRequest(List<Lottery> lotteries) {
        if (this.lotteries.size()<MAX_SIZE){
            this.lotteries.addAll(lotteries);
            Date date = lotteries.get(lotteries.size() - 1).getTime();
            date.setTime(date.getTime() - ONE_MINITE);
            handler.requestLottery(date, this);
        }
    }
}
