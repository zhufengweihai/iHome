package com.zf.lottery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class PullDownView extends FrameLayout {
    public static final String TAG = "PullDownLayout";
    private static final float DEFAULT_PULL_DOWN_HEIGHT = 50;
    private static final float DEFAULT_PULL_UP_HEIGHT = 50;

    private View topView;
    private boolean isonce;
    private MarginLayoutParams layoutParams;

    private float down = 0;

    public PullDownView(Context context) {
        this(context, null);
    }

    public PullDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!isonce) {
            topView = getChildAt(1);
            topView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            down = event.getY();
                        case MotionEvent.ACTION_MOVE:
                            moveDown((int) (event.getY() - down));
                            down = event.getY();
                            break;
                    }
                    return true;
                }
            });
            isonce = true;
            layoutParams = (MarginLayoutParams) topView.getLayoutParams();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down = ev.getY();
            case MotionEvent.ACTION_MOVE:
                moveDown((int) (ev.getY() - down));
                down = ev.getY();
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void moveDown(int distance) {
        layoutParams.topMargin += distance;
        topView.setLayoutParams(layoutParams);
    }
}
