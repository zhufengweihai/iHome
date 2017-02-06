package com.zf.measurement.unit;

/**
 * Created by zhufeng7 on 2016-9-20.
 */

public enum MassUnit {
    t(1000, "t"), q(100, "q"), kg(1, "kg"), g(0.001, "g"), ct(0.0002, "ct"), mg(0.000001, "mg"), ug(0.000000001, "ug"),
    lt(1016.04691, "lt"), st(907.18474, "st"), lh(50.8023455, "lh"), sh(45.359237, "sh"), stone(6.3502931875, "stone"),
    lb(0.45359237, "lb"), oz(0.028349523125, "oz"), dr(0.0017718451953125, "dr"), gr(0.00006479891, "gr"),
    dan(50, "dan"), jin(0.5, "jin"), liang(0.05, "liang"), qian(0.005, "qian"), fen(0.0005, "fen");

    private double scale = 0;
    private double value = 0;
    private String unit = null;

    private MassUnit(double scale, String unit) {
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

    public static MassUnit getUnit(String name) {
        for (MassUnit unit : values()) {
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
        return new String[]{t.unit, q.unit, kg.unit, g.unit, ct.unit, mg.unit, ug.unit};
    }

    public static String[] getBritish() {
        return new String[]{lt.unit, st.unit, lh.unit, sh.unit, stone.unit, lb.unit, oz.unit, dr.unit, gr.unit};
    }

    public static String[] getChinese() {
        return new String[]{dan.unit, jin.unit, liang.unit, qian.unit, fen.unit};
    }
}
