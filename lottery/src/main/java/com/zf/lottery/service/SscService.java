package com.zf.lottery.service;

import android.content.Context;
import android.os.Handler;

import com.zf.lottery.common.Commons;
import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.dao.impl.LotteryDbHelper;
import com.zf.lottery.data.Lottery;
import com.zf.lottery.view.help.DataHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.zf.lottery.service.impl.SscStatServiceImpl.MAX_NUM;

/**
 * Created by Administrator on 2017/3/26 0026.
 */

public class SscService implements LotteryResultsListener {
    private static final int ONE_MINITE = 60 * 1000;
    private static final int MIN_SIZE = 1000;
    private static final int MAX_SIZE = 11000;
    private static final int SIZE = MAX_SIZE - MIN_SIZE;

    private Handler handler = null;
    private LotteryDbHelper dbHelper = null;
    private List<Lottery> historyLotteries = null;
    private List<Lottery> lotteries = new ArrayList<>(MAX_SIZE);
    private SscRequestHandler requestHandler = new SscRequestHandler();

    public SscService(Context context, Handler handler) {
        dbHelper = new LotteryDbHelper(context);
        this.handler = handler;
    }

    public void requestLottery() {
        historyLotteries = dbHelper.readSscResult();
        if (historyLotteries.size() < MIN_SIZE) {
            requestHandler.requestLottery(new Date(), this);
        } else {

        }
    }

    @Override
    public void onRequest(List<Lottery> lotteries) {
        if (this.lotteries.size() < MAX_SIZE) {
            this.lotteries.addAll(lotteries);
            Date date = lotteries.get(lotteries.size() - 1).getTime();
            requestHandler.requestLottery(new Date(date.getTime() - ONE_MINITE), this);
        } else {
            calcAbence();
            trimLotteries();
            calcMaxAbence();
            DataHelper.getInstance().save(this.lotteries);
            handler.sendEmptyMessage(Commons.MSG_HANDLER_EMPTY);
        }
    }

    private void calcAbence() {
        int[] absences = new int[MAX_NUM];
        int l = lotteries.size() - 1;
        for (int i = l; i >= 0; i--) {
            Lottery lottery = lotteries.get(i);
            for (int j = 0; j < MAX_NUM; j++) {
                absences[j]++;
            }
            absences[lottery.getSum()] = 0;
            lottery.setAbsences(absences);
        }
    }

    private void trimLotteries() {
        List<Lottery> newList = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            newList.add(lotteries.get(i));
        }
        lotteries = newList;
    }

    private void calcMaxAbence() {
        for (Lottery lottery : lotteries) {
            lottery.setMaxAbence(max(lottery.getAbsences()));
        }
    }

    private int max(int[] input) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < input.length; i++) {
            if (max < input[i]) {
                max = input[i];
            }
        }
        return max;
    }
}
