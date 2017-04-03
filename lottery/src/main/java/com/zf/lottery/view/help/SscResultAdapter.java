package com.zf.lottery.view.help;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zf.lottery.data.Lottery;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import de.codecrafters.tableview.TableDataAdapter;

/**
 * Created by zhufeng7 on 2017-4-1.
 */

public class SscResultAdapter extends TableDataAdapter<Lottery> {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public SscResultAdapter(Context context, List<Lottery> data) {
        super(context, data);
    }

    @Override
    public View getCellView(int rowIndex, int columnIndex, ViewGroup parentView) {
        final TextView textView = new TextView(getContext());
        Lottery lottery = getRowData(rowIndex);
        String text = null;
        switch (columnIndex) {
            case 0:
                text = dateFormat.format(lottery.getTime()).substring(11);
                break;
            case 1:
                text = String.valueOf(lottery.getTerm() % 1000);
                break;
            case 2:
                text = getNumberString(lottery.getNumbers());
                break;
        }
        textView.setText(text);
        return textView;
    }

    private String getNumberString(int[] numbers) {
        int iMax = numbers.length - 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; ; i++) {
            sb.append(numbers[i]);
            if (i == iMax) return sb.toString();
            sb.append("     ");
        }
    }

    @Override
    public int getCount() {
        return getData() == null ? 0 : super.getCount();
    }
}
