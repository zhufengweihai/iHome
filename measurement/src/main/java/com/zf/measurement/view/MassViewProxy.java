package com.zf.measurement.view;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zf.measurement.R;
import com.zf.measurement.unit.MassUnit;

import static com.zf.measurement.unit.MassUnit.jin;
import static com.zf.measurement.unit.MassUnit.kg;
import static com.zf.measurement.unit.MassUnit.lb;

/**
 * Created by zhufeng7 on 2016-9-20.
 */

public class MassViewProxy extends MeasurementViewProxy {


    public MassViewProxy(LayoutInflater inflater, ViewGroup root) {
        super(inflater, root);
    }

    @Override
    public String[][] getUnitsAndNotes() {
        Resources r = context.getResources();
        return new String[][]{MassUnit.getMetric(), r.getStringArray(R.array.note_mass_metric), MassUnit.getBritish()
                , r.getStringArray(R.array.note_mass_british), MassUnit.getChinese(), r.getStringArray(R.array
                .note_mass_chinese)};
    }

    @Override
    public String[] getUnits() {
        return new String[]{kg.getUnit(), lb.getUnit(), jin.getUnit()};
    }

    @Override
    public double toUnitValue(String unit, double originalValue) {
        return MassUnit.getUnit(unit).toValue(originalValue);
    }

    @Override
    public double toOriginalValue(String unit, double value) {
        return MassUnit.getUnit(unit).toOriginalValue(value);
    }
}
