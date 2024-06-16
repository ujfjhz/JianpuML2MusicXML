package org.chinesemusicscore.JianpuML.property;

public class IdentificationProperty {
    public static String JP_PROP_COMPOSER = "Composer";
    public static String JP_PROP_ARRANGER = "Arranger";

    private String composer = "Composer";
    private String arranger = "ChineseMusicScore";


    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }

    public String getArranger() {
        return arranger;
    }

    public void setArranger(String arranger) {
        this.arranger = arranger;
    }
}
