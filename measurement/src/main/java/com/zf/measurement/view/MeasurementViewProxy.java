package com.zf.measurement.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zf.common.widget.PopupWindowEx.SelectedListener;
import com.zf.measurement.R;
import com.zf.measurement.SelectUnitPopupWindow;

/**
 * Created by zhufeng7 on 2016-9-20.
 */

public abstract class MeasurementViewProxy {
    private static final int DEFAULT_VALUE = 1;
    private static final String DEFAULT_STRING = "1";

    private double m = DEFAULT_VALUE;
    protected Context context = null;
    private View view = null;
    private EditText metricText;
    private EditText britishText;
    private EditText chineseText;
    private Button metricButton;
    private Button britishButton;
    private Button chineseButton;


    public MeasurementViewProxy(LayoutInflater inflater, ViewGroup root) {
        init(inflater, root);
    }

    public View getView() {
        return view;
    }

    private void init(LayoutInflater inflater, ViewGroup root) {
        view = inflater.inflate(R.layout.layout_length, root, false);
        context = root.getContext();
        metricText = (EditText) view.findViewById(R.id.metricText);
        britishText = (EditText) view.findViewById(R.id.britishText);
        chineseText = (EditText) view.findViewById(R.id.chineseText);
        initUnitsButton();
        initNumberButton();
        initBackButton();
        initClearButton();
    }

    public abstract String[][] getUnitsAndNotes();

    public abstract String[] getUnits();

    protected void initUnitsButton() {
        String[][] s = getUnitsAndNotes();
        String[] units = getUnits();
        metricButton = (Button) view.findViewById(R.id.metricButton);
        metricButton.setText(units[0]);
        metricButton.setOnClickListener(createUnitButtonListener(metricButton, metricText, s[0], s[1]));
        britishButton = (Button) view.findViewById(R.id.britishButton);
        britishButton.setText(units[1]);
        britishButton.setOnClickListener(createUnitButtonListener(britishButton, britishText, s[2], s[3]));
        chineseButton = (Button) view.findViewById(R.id.chineseButton);
        chineseButton.setText(units[2]);
        chineseButton.setOnClickListener(createUnitButtonListener(chineseButton, chineseText, s[4], s[5]));
    }

    private OnClickListener createUnitButtonListener(final Button button, final EditText editText, final String[]
            units, final String[] notes) {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair<String, String>[] pairs = new Pair[units.length];
                for (int i = 0; i < units.length; i++) {
                    pairs[i] = new Pair<>(units[i], notes[i]);
                }
                SelectUnitPopupWindow selectWindow = new SelectUnitPopupWindow(context, pairs);
                selectWindow.setSelectedListener(createSelectedListener(button, editText));
                selectWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        };
    }

    private void initNumberButton() {
        OnClickListener listener = createListener();
        View[] buttons = new View[]{view.findViewById(R.id.oneButton), view.findViewById(R.id.twoButton), view
                .findViewById(R.id.threeButton), view.findViewById(R.id.fourButton), view.findViewById(R.id
                .fiveButton), view.findViewById(R.id.sixButton), view.findViewById(R.id.sevenButton), view
                .findViewById(R.id.eigthButton), view.findViewById(R.id.nineButton), view.findViewById(R.id
                .zeroButton), view.findViewById(R.id.pointButton)};
        for (View button : buttons) {
            button.setOnClickListener(listener);
        }
    }

    private void initBackButton() {
        Button backButton = (Button) view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }

    private void initClearButton() {
        Button clearButton = (Button) view.findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    @NonNull
    private OnClickListener createListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                append(((Button) v).getText());
            }
        };
    }

    private void append(CharSequence number) {
        if (metricText.hasFocus()) {
            metricText.append(number);
        } else if (britishText.hasFocus()) {
            britishText.append(number);
        } else if (chineseText.hasFocus()) {
            chineseText.append(number);
        }
        updateValues();
    }

    private void delete() {
        EditText editText = null;
        if (metricText.hasFocus()) {
            editText = metricText;
        } else if (britishText.hasFocus()) {
            editText = britishText;
        } else if (chineseText.hasFocus()) {
            editText = chineseText;
        }
        int index = editText.getSelectionStart();   //获取Edittext光标所在位置
        String str = editText.getText().toString();
        if (!str.equals("")) {//判断输入框不为空，执行删除
            editText.getText().delete(index - 1, index);
        }
        updateValues();
    }

    private void clear() {
        if (metricText.hasFocus()) {
            metricText.setText(DEFAULT_STRING);
        } else if (britishText.hasFocus()) {
            britishText.setText(DEFAULT_STRING);
        } else if (chineseText.hasFocus()) {
            chineseText.setText(DEFAULT_STRING);
        }
        updateValues();
    }

    public SelectedListener createSelectedListener(final Button nameButton, final EditText valueView) {
        return new SelectedListener() {
            @Override
            public void onSelected(String result) {
                nameButton.setText(result);
                double v = toUnitValue(result, m);
                valueView.setText(String.valueOf(v));
            }
        };
    }

    private void updateValues() {
        if (metricText.hasFocus()) {
            updateValues(metricButton, metricText, britishButton, britishText, chineseButton, chineseText);
        } else if (britishText.hasFocus()) {
            updateValues(britishButton, britishText, metricButton, metricText, chineseButton, chineseText);
        } else if (chineseText.hasFocus()) {
            updateValues(chineseButton, chineseText, metricButton, metricText, britishButton, britishText);
        }
    }

    private void updateValues(Button updatedButton, EditText updatedEdit, Button toUpdateButton1, EditText
            toUpdateEdit1, Button toUpdateButton2, EditText toUpdateEdit2) {
        String updatedUnit = updatedButton.getText().toString();
        double updatedValue = Double.parseDouble(updatedEdit.getText().toString());
        String toUpdateUnit1 = toUpdateButton1.getText().toString();
        String toUpdateUnit2 = toUpdateButton2.getText().toString();

        m = toOriginalValue(updatedUnit, updatedValue);
        double v1 = toUnitValue(toUpdateUnit1, m);
        toUpdateEdit1.setText(String.valueOf(v1));
        double v2 = toUnitValue(toUpdateUnit2, m);
        toUpdateEdit2.setText(String.valueOf(v2));
    }

    public abstract double toUnitValue(String unit, double originalValue);

    public abstract double toOriginalValue(String unit, double value);
}
