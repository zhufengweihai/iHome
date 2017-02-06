package com.zf.lottery.parse;

import java.util.List;

/**
 * Created by zhufeng7 on 2016-6-30.
 */

public interface LotteryResolveListener {
    void onResolved(List<LotteryGroup> groups);
}
