package com.zf.ihome.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zf.common.Utils;

/**
 * Created by zhufeng7 on 2016-1-25.
 */
public abstract class HomeCardView extends CardView {
    public HomeCardView(Context context) {
        super(context);
    }

    public HomeCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(linearLayout);
        LinearLayout titleLinearLayout = new LinearLayout(getContext());
        titleLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, Utils.dip2px(getContext(), 10)));
        //titleLinearLayout.setBackgroundResource(R.color.colorCardTile);
        //titleLinearLayout.addView(new TextView());
    }

    public abstract int getTitleIconId();
}
