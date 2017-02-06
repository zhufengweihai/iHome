package com.zf.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zf.common.R;

public class TextViewEx extends TextView {

    public TextViewEx(Context context) {
        super(context);
    }

    public TextViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewEx);
        Drawable[] drawables = getCompoundDrawables();
        for (Drawable drawable : drawables) {
            if (drawable != null) {
                int right = a.getLayoutDimension(R.styleable.TextViewEx_drawableWidth, 0);
                int bottom = a.getLayoutDimension(R.styleable.TextViewEx_drawableHeight, 0);
                drawable.setBounds(0, 0, right, bottom);
            }
        }

        setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
        a.recycle();
    }
}
