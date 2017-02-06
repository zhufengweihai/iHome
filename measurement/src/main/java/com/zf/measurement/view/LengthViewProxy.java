package com.zf.measurement.view;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zf.measurement.R;
import com.zf.measurement.unit.LengthUnit;

/**
 * Created by zhufeng7 on 2016-9-20.
 */

public class LengthViewProxy extends MeasurementViewProxy {

    public LengthViewProxy(LayoutInflater inflater, ViewGroup root) {
        super(inflater, root);
    }

    @Override
    public String[][] getUnitsAndNotes() {
        Resources r = context.getResources();
        return new String[][]{LengthUnit.getMetric(), r.getStringArray(R.array.note_length_metric), LengthUnit
                .getBritish(), r.getStringArray(R.array.note_length_british), LengthUnit.getChinese(), r
                .getStringArray(R.array.note_length_chinese)};
    }

    @Override
    public String[] getUnits() {
        return new String[]{"m", "ft", "chi"};
    }

    @Override
    public double toUnitValue(String unit, double originalValue) {
        return LengthUnit.getUnit(unit).toValue(originalValue);
    }

    @Override
    public double toOriginalValue(String unit, double value) {
        return LengthUnit.getUnit(unit).toOriginalValue(value);
    }
}
