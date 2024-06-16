package org.chinesemusicscore.JianpuML.property;

public class AttributeProperty {
    public static String JP_PROP_KEY = "Key";
    public static String JP_PROP_TimeSignature = "TimeSignature";

    private String key = "";
    private String timeSignature = "";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTimeSignature() {
        return timeSignature;
    }

    public void setTimeSignature(String timeSignature) {
        this.timeSignature = timeSignature;
    }
}
