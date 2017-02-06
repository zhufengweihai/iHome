package com.zf.measurement.unit;

/**
 * Created by zhufeng7 on 2016-9-20.
 */

public enum LengthUnit {
    ly(9460528404879358.8126, "ly"), au(149597870700l, "au"), km(1000, "km"), m(1, "m"), dm(0.1, "dm"),
    cm(0.01, "cm"), mm(0.001, "mm"), um(0.000001, "um"), nm(0.000000001, "nm"), pm(0.000000000001, "pm"),
    nmi(1852, "nmi"), mi(1609.344, "mi"), fur(201.168, "fur"), chn(20.1168, "chn"), rod(5.0292, "rod"),
    fm(1.8288, "fm"), yd(0.9144, "yd"), ft(0.3048, "ft"), hand(0.1016, "hand"), in(0.0254, "in"), Li(500, "lǐ"),
    zhang(10 / 3.0, "zh"), chi(1 / 3.0, "chi"), cun(1 / 30.0, "cun"), fen(1 / 300.0, "fen"), li(1 / 3000.0, "lí"),
    hao(1 / 30000.0, "hao");

    private double scale = 0;
    private double value = 0;
    private String unit = null;

    private LengthUnit(double scale, String unit) {
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

    public static LengthUnit getUnit(String name) {
        for (LengthUnit unit : values()) {
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
        return new String[]{ly.unit, au.unit, km.unit, m.unit, dm.unit, cm.unit, mm.unit, um.unit, nm.unit, pm.unit};
    }

    public static String[] getBritish() {
        return new String[]{nmi.unit, mi.unit, fur.unit, chn.unit, rod.unit, fm.unit, yd.unit, ft.unit, hand.unit, in
                .unit};
    }

    public static String[] getChinese() {
        return new String[]{Li.unit, zhang.unit, chi.unit, cun.unit, fen.unit, li.unit, hao.unit};
    }
}
