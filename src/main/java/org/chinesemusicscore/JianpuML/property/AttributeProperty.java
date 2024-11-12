package org.chinesemusicscore.JianpuML.property;

public class AttributeProperty {
    public static String JP_PROP_KEY = "Key";
    public static String JP_PROP_TimeSignature = "TimeSignature";
    public static String JP_PROP_STAFF = "Staff";

    private String key = "";
    private String timeSignature = "";
    private String staff = null;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }
}
