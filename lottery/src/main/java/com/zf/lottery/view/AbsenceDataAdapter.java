package com.zf.lottery.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zf.lottery.data.Abence;

import java.util.List;
import java.util.Locale;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by zhufeng7 on 2017-4-1.
 */

public class AbsenceDataAdapter extends TableDataAdapter<Abence> {
    public AbsenceDataAdapter(Context context, List<Abence> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final TextView textView = new TextView(getContext());
        Abence ga = getRowData(rowIndex);
        String text = null;
        switch (columnIndex) {
            case 0:
                text = String.format(Locale.getDefault(), "%02d", ga.getNum1());
                break;
            case 1:
                text = String.valueOf(ga.getAbsence1());
                break;
            case 2:
                text = String.format(Locale.getDefault(), "%02d", ga.getNum2());
                break;
            case 3:
                text = String.valueOf(ga.getAbsence2());
                break;
            case 4:
                text = String.valueOf(ga.getTotalAbence());
                break;
        }
        textView.setText(text);
        return textView;
    }

    @Override
    public int getCount() {
        return getData() == null ? 0 : super.getCount();
    }
}
