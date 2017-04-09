package com.zf.lottery.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zf.lottery.data.Absence;
import com.zf.lottery.data.GroupAbsence;

import java.util.List;
import java.util.Locale;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by zhufeng7 on 2017-4-1.
 */

public class GroupAbsenceAdapter extends TableDataAdapter<GroupAbsence> {
    public GroupAbsenceAdapter(Context context, List<GroupAbsence> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final TextView textView = new TextView(getContext());
        GroupAbsence ga = getRowData(rowIndex);
        String text = null;
        Absence absence = null;
        switch (columnIndex) {
            case 0:
                absence = ga.getAbsence(0);
                text = String.format(Locale.getDefault(), "%d%d", absence.getN1(), absence.getN2());
                break;
            case 1:
                absence = ga.getAbsence(0);
                text = String.format(Locale.getDefault(), "%d", absence.getAbsence());
                break;
            case 2:
                absence = ga.getAbsence(1);
                if (absence != null) {
                    text = String.format(Locale.getDefault(), "%d%d", absence.getN1(), absence.getN2());
                }
                break;
            case 3:
                absence = ga.getAbsence(1);
                if (absence != null) {
                    text = String.format(Locale.getDefault(), "%d", absence.getAbsence());
                }
                break;
            case 4:
                text = String.valueOf(ga.getGroupAbsence());
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
