package com.zf.lottery.view;

import android.content.Intent;
import android.os.Bundle;

import com.zf.common.app.LoadingActivity;
import com.zf.lottery.common.Commons;
import com.zf.lottery.dao.LotteryClassDao;
import com.zf.lottery.dao.LotteryResultsListener;
import com.zf.lottery.dao.impl.LotteryClassDaoImpl;
import com.zf.lottery.data.Lottery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhufeng7 on 2017-2-21.
 */

public class LotteryLoadingActivity extends LoadingActivity implements LotteryResultsListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LotteryClassDao lotteryClassDao = new LotteryClassDaoImpl();
        lotteryClassDao.requestLotteryResults(this);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onRequest(List<Lottery> lotteries) {
        if (lotteries.size() >= Commons.MAX_LOTTERY_TERM) {
            Intent intent = new Intent();
            intent.putExtra(Commons.RETURN_VALUE_LOTTERY_RESULT, (ArrayList) lotteries);
            setResult(Commons.REQUEST_CODE_LOTTERY_RESULT, intent);
            finish();
        }
    }
}
