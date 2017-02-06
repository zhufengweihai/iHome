package com.zf.common.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by zhufeng7 on 2016-11-2.
 */

public class RadarScanLayout extends FrameLayout {
    public RadarScanLayout(Context context) {
        super(context);
        init();
    }

    public RadarScanLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadarScanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(new RadarScanView(getContext()), lp);
    }

    public void addTarget(String name, @IdRes int iconId) {

    }
}
