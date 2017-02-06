package com.zf.common.widget;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by zhufeng7 on 2016-9-13.
 */

public class PopupWindowEx extends PopupWindow implements View.OnTouchListener {
    public PopupWindowEx(Context context) {
        super(context);
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void setContentView(View contentView) {
        super.setContentView(contentView);
        contentView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int height = getContentView().getTop();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (y < height) {
                dismiss();
            }
        }
        return true;
    }

    public static interface SelectedListener {
        void onSelected(String result);
    }
}
