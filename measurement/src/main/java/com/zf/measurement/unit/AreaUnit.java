package com.zf.measurement.unit;

/**
 * Created by zhufeng7 on 2016-9-20.
 */

public enum AreaUnit {
    km2(1000000, "km²"), ha(10000, "ha"), are(100, "are"), m2(1, "m²"), dm2(0.01, "dm²"), cm2(0.0001, "cm²"),
    mm2(0.000001, "mm²"), mi2(2589988.110336, "mi²"), acre(4046.8564224, "acre"), chn2(404.685642, "chn²"),
    rod2(25.2928526, "rod²"), yd2(0.83612736, "yd²"), ft2(0.09290304, "ft²"), in2(0.00064516, "in²"),
    qing(200000 / 3.0, "qing"), mu(2000 / 3.0, "mu"), fen(200 / 3.0, "fen"), li(20 / 3.0, "li");

    private double scale = 0;
    private double value = 0;
    private String unit = null;

    private AreaUnit(double scale, String unit) {
        this.scale = scale;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double toValue(double m) {
        return m / scale;
    }

    public double toOriginalValue(double value) {
        return value * scale;
    }

    public static AreaUnit getUnit(String name) {
        for (AreaUnit unit : values()) {
            if (unit.unit.equals(name)) {
                return unit;
            }
        }
        return null;
    }

    public String getUnit() {
        return unit;
    }

    public static String[] getMetric() {
        return new String[]{km2.unit, ha.unit, are.unit, m2.unit, dm2.unit, cm2.unit, mm2.unit};
    }

    public static String[] getBritish() {
        return new String[]{mi2.unit, acre.unit, chn2.unit, rod2.unit, yd2.unit, ft2.unit, in2.unit};
    }

    public static String[] getChinese() {
        return new String[]{qing.unit, mu.unit, fen.unit, li.unit};
    }
}
