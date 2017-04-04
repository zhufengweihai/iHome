package com.zf.lottery.service;

import android.content.Context;

import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.dao.impl.LotteryDbHelper;
import com.zf.lottery.data.Absence;
import com.zf.lottery.data.Lottery;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.zf.lottery.service.impl.SscStatServiceImpl.MAX_NUM;

/**
 * Created by Administrator on 2017/3/26 0026.
 */

public class SscService implements LotteryResultsListener {
    private static final int ONE_MINITE = 60 * 1000;
    private static final int MIN_SIZE = 1000;
    private static final int MAX_SIZE = 11000;
    private static final int SIZE = MAX_SIZE - MIN_SIZE;

    private LotteryResultListener listener = null;
    private LotteryDbHelper dbHelper = null;
    private List<Lottery> historyLotteries = null;
    private List<Lottery> lotteries = new ArrayList<>(MAX_SIZE);
    private SscRequestHandler requestHandler = new SscRequestHandler();
    private Date lastDate = null;

    public SscService(Context context, LotteryResultListener listener) {
        dbHelper = new LotteryDbHelper(context);
        this.listener = listener;
    }

    public void requestLottery() {
        historyLotteries = dbHelper.readSscResult();
        if (historyLotteries.size() > MIN_SIZE) {
            lastDate = historyLotteries.get(0).getTime();
        }
        requestHandler.requestLottery(new Date(), this);
    }

    @Override
    public void onRequest(List<Lottery> lotteries) {
        if (lastDate != null) {
            if (addLotteries(lotteries)) {
                requestNext(lotteries);
            } else {
                dbHelper.deleteOldAndSaveNewResult(this.lotteries);
                this.lotteries.addAll(historyLotteries);
                calcAbence();
                trimLotteries();
                listener.onRequest(this.lotteries);
            }
        } else if (this.lotteries.size() < MAX_SIZE) {
            this.lotteries.addAll(lotteries);
            requestNext(lotteries);
        } else {
            dbHelper.saveSscResult(this.lotteries);
            calcAbence();
            trimLotteries();
            listener.onRequest(this.lotteries);
        }
    }

    private void requestNext(List<Lottery> lotteries) {
        Date date = lotteries.get(lotteries.size() - 1).getTime();
        if (this.lotteries.size() % 900 == 0) {
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        requestHandler.requestLottery(new Date(date.getTime() - ONE_MINITE), this);
    }

    private boolean addLotteries(List<Lottery> lotteries) {
        for (Lottery lottery : lotteries) {
            if (lottery.getTime().after(lastDate)) {
                this.lotteries.add(lottery);
            } else {
                return false;
            }
        }
        return true;
    }

    private void calcAbence() {
        List<Absence> abenceList = new ArrayList<>(MAX_NUM);
        for (int m = 0; m < 10; m++) {
            for (int n = 0; n < 10; n++) {
                abenceList.add(new Absence(m, n));
            }
        }
        int l = lotteries.size() - 1;
        for (int i = l; i >= 0; i--) {

            Lottery lottery = lotteries.get(i);
            for (int m = 0; m < 10; m++) {
                for (int n = 0; n < 10; n++) {
                    abenceList.get(m * 10 + n).increaseAbsence();
                }
            }
            int[] numbers = lottery.getNumbers();
            abenceList.get(numbers[3] * 10 + numbers[4]).setAbsence(0);

            List<Absence> absences = new ArrayList<>(MAX_NUM);
            for (Absence absence : abenceList) {
                absences.add(new Absence(absence));
            }
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
