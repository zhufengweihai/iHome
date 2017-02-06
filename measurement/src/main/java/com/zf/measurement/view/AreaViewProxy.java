package com.zf.measurement.view;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zf.measurement.R;
import com.zf.measurement.unit.AreaUnit;

import static com.zf.measurement.unit.AreaUnit.ft2;
import static com.zf.measurement.unit.AreaUnit.m2;
import static com.zf.measurement.unit.AreaUnit.mu;

/**
 * Created by zhufeng7 on 2016-9-20.
 */

public class AreaViewProxy extends MeasurementViewProxy {


    public AreaViewProxy(LayoutInflater inflater, ViewGroup root) {
        super(inflater, root);
    }

    @Override
    public String[][] getUnitsAndNotes() {
        Resources r = context.getResources();
        return new String[][]{AreaUnit.getMetric(), r.getStringArray(R.array.note_area_metric), AreaUnit.getBritish()
                , r.getStringArray(R.array.note_area_british), AreaUnit.getChinese(), r.getStringArray(R.array
                .note_area_chinese)};
    }

    @Override
    public String[] getUnits() {
        return new String[]{m2.getUnit(), ft2.getUnit(), mu.getUnit()};
    }

    @Override
    public double toUnitValue(String unit, double originalValue) {
        return AreaUnit.getUnit(unit).toValue(originalValue);
    }

    @Override
    public double toOriginalValue(String unit, double value) {
        return AreaUnit.getUnit(unit).toOriginalValue(value);
    }
}
