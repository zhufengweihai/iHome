package com.zf.lottery.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.zf.lottery.R;

public class PullDownView extends FrameLayout {
    public static final String TAG = "PullDownLayout";
    private static final float DEFAULT_PULL_DOWN_HEIGHT = 50;
    private static final float DEFAULT_PULL_UP_HEIGHT = 50;

    private int touchSlop;
    private int screenHeight;
    private ViewGroup parentView;
    private View topView;
    private int topViewHeight;
    private View containerView;
    private float origY;
    private float distanceY;
    private boolean isShowTopView;
    private int mCurrentOffset = 0;
    private boolean isonce;
    private MarginLayoutParams layoutParams;
    private int oldTopMargin;
    private boolean isTouchOne;
    private boolean isTop;
    private ValueAnimator valueAnimator;
    private boolean isChangeSpeed;
    private float pullDownHeight;
    private float pullUpHeight;

    public PullDownView(Context context) {
        this(context, null);
    }

    public PullDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        topViewHeight = screenHeight;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        getAttrs(context, attrs, defStyleAttr);
    }

    private void getAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.pullDownLayout, defStyleAttr, 0);
        isChangeSpeed = typedArray.getBoolean(R.styleable.pullDownLayout_is_change_speed, false);
        pullDownHeight = typedArray.getDimension(R.styleable.pullDownLayout_pull_down_height, DEFAULT_PULL_DOWN_HEIGHT);
        pullUpHeight = typedArray.getDimension(R.styleable.pullDownLayout_pull_up_height, DEFAULT_PULL_UP_HEIGHT);
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!isonce) {
            parentView = (ViewGroup) this.getChildAt(0);
            topView = parentView.getChildAt(0);
            containerView = parentView.getChildAt(1);
            topView.getLayoutParams().height = topViewHeight;
            isonce = true;

            layoutParams = (MarginLayoutParams) containerView.getLayoutParams();
            oldTopMargin = layoutParams.topMargin;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!isTouchOne) {
                    origY = ev.getRawY();
                    isTouchOne = true;
                    isTop = getScrollY() == 0;
                }
                distanceY = ev.getRawY() - origY;
                if (distanceY < touchSlop && isShowTopView) {
                    return true;
                }
                if (distanceY < touchSlop) {
                    return super.onTouchEvent(ev);
                }
                if (isTop && distanceY > 0 && !isShowTopView) {
                    moveDown((int) distanceY / 2);
                }
                break;
            case MotionEvent.ACTION_UP:
                isTouchOne = false;
                if (isTop && distanceY > pullDownHeight && !isShowTopView) {
                    pullDown();
                } else if (distanceY > 0 && distanceY < pullUpHeight && !isShowTopView) {
                    pullUp();
                } else if (distanceY < 0 && isShowTopView) {
                    pullUp();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void pullDown() {
        animate(layoutParams.topMargin, screenHeight);
        isShowTopView = true;
    }

    private void pullUp() {
        animate(layoutParams.topMargin, oldTopMargin);
        isShowTopView = false;
    }

    private void moveDown(int distance) {
        layoutParams.topMargin = distance + oldTopMargin;
        containerView.setLayoutParams(layoutParams);
    }

    private void animate(int start, int end) {
        valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                layoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                containerView.setLayoutParams(layoutParams);
            }
        });
    }
}
