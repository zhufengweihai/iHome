package com.zf.lottery.view.help;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zf.lottery.data.Absence;

import java.util.List;
import java.util.Locale;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by zhufeng7 on 2017-4-1.
 */

public class AbsenceAdapter extends TableDataAdapter<Absence> {
    public AbsenceAdapter(Context context, List<Absence> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final TextView textView = new TextView(getContext());
        Absence absence = getRowData(rowIndex);
        String text = null;
        switch (columnIndex) {
            case 0:
                text = String.format(Locale.getDefault(), "%02d", absence.getN1());
                break;
            case 1:
                text = String.format(Locale.getDefault(), "%d", absence.getAbsence());
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
