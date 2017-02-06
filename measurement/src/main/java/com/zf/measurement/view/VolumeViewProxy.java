package com.zf.measurement.view;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zf.measurement.R;
import com.zf.measurement.unit.VolumeUnit;

import static com.zf.measurement.unit.VolumeUnit.L;
import static com.zf.measurement.unit.VolumeUnit.ft3;
import static com.zf.measurement.unit.VolumeUnit.m3;

/**
 * Created by zhufeng7 on 2016-9-20.
 */

public class VolumeViewProxy extends MeasurementViewProxy {


    public VolumeViewProxy(LayoutInflater inflater, ViewGroup root) {
        super(inflater, root);
    }

    @Override
    public String[][] getUnitsAndNotes() {
        Resources r = context.getResources();
        return new String[][]{VolumeUnit.getMetric(), r.getStringArray(R.array.note_volume_metric), VolumeUnit
                .getBritish(), r.getStringArray(R.array.note_volume_british), VolumeUnit.getChinese(), r
                .getStringArray(R.array.note_volume_chinese)};
    }

    @Override
    public String[] getUnits() {
        return new String[]{m3.getUnit(), ft3.getUnit(), L.getUnit()};
    }

    @Override
    public double toUnitValue(String unit, double originalValue) {
        return VolumeUnit.getUnit(unit).toValue(originalValue);
    }

    @Override
    public double toOriginalValue(String unit, double value) {
        return VolumeUnit.getUnit(unit).toOriginalValue(value);
    }
}
