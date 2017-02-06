package com.zf.measurement;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zf.common.widget.PopupWindowEx;

/**
 * Created by zhufeng7 on 2016-9-13.
 */

public class SelectUnitPopupWindow extends PopupWindowEx {
    private SelectedListener listener = null;

    public SelectUnitPopupWindow(Context context, Pair<String, String>[] unitPairs) {
        super(context);
        View.OnClickListener listener = createOnClickListener();
        LayoutInflater inflater = LayoutInflater.from(context);
        GridLayout gridLayout = (GridLayout) inflater.inflate(R.layout.layout_select_units, null, false);
        for (Pair<String, String> pair : unitPairs) {
            View view = inflater.inflate(R.layout.layout_unit, gridLayout, false);
            TextView unitNameView = (TextView) view.findViewById(R.id.unitNameView);
            unitNameView.setText(pair.first);
            unitNameView.setOnClickListener(listener);
            ((TextView) view.findViewById(R.id.unitNoteView)).setText(pair.second);
            gridLayout.addView(view);
        }
        setContentView(gridLayout);
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.colorBackground));
        setBackgroundDrawable(dw);
    }

    public void setSelectedListener(SelectedListener listener) {
        this.listener = listener;
    }

    private View.OnClickListener createOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                listener.onSelected(((TextView) v).getText().toString());
            }
        };
    }
}
