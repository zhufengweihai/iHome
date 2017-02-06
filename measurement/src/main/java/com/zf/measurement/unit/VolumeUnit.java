package com.zf.measurement.unit;

/**
 * Created by zhufeng7 on 2016-9-20.
 */

public enum VolumeUnit {
    km3(1000000000, "km³"), m3(1, "m³"), dm3(0.001, "dm³"), cm3(0.000001, "cm³"), mm3(0.000000001, "mm³"),
    hl(0.1, "hl"), dal(0.01, "dal"), L(0.001, "L"), dl(0.0001, "dl"), cl(0.00001, "cl"), ml(0.000001, "ml"),
    acft(1233.4818375, "acft"), yd3(0.7645536, "yd³"), ft3(0.0283168, "ft³"), in3(0.000016387064, "in³"),
    UKbbl(0.16365931, "UK bbl"), UKgal(0.00454609, "UK gal"), UKpt(0.00056826125, "UK pt"),
    USbbl(0.15898729, "US bbl"), USgal(0.00378541, "US gal"), USpt(0.00047317625, "US gal");

    private double scale = 0;
    private double value = 0;
    private String unit = null;

    private VolumeUnit(double scale, String unit) {
        this.scale = scale;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double toValue(double originalValue) {
        return (double) Arith.div(originalValue, scale);
    }

    public double toOriginalValue(double value) {
        return (double) Arith.mul(value, scale);
    }

    public static VolumeUnit getUnit(String name) {
        for (VolumeUnit unit : values()) {
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
        return new String[]{km3.unit, m3.unit, dm3.unit, cm3.unit, mm3.unit};
    }

    public static String[] getBritish() {
        return new String[]{acft.unit, yd3.unit, ft3.unit, in3.unit, UKbbl.unit, UKgal.unit, UKpt.unit, USbbl.unit,
                USgal.unit, USpt.unit};
    }

    public static String[] getChinese() {
        return new String[]{hl.unit, dal.unit, L.unit, dl.unit, cl.unit, ml.unit};
    }
}