package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CbPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    public static Map<String, BigDecimal> alterMap = new HashMap<>();

    static {
        Pitch cFlat = new Pitch();
        cFlat.setStep(Step.C);
        cFlat.setAlter(new BigDecimal("-1"));
        cFlat.setOctave(4);
        pitchMap.put("1",cFlat);
        alterMap.put(Step.C.name(), cFlat.getAlter());

        Pitch dFlat = new Pitch();
        dFlat.setStep(Step.D);
        dFlat.setAlter(new BigDecimal("-1"));
        dFlat.setOctave(4);
        pitchMap.put("2",dFlat);
        alterMap.put(Step.D.name(), dFlat.getAlter());

        Pitch eFlat = new Pitch();
        eFlat.setStep(Step.E);
        eFlat.setAlter(new BigDecimal("-1"));
        eFlat.setOctave(4);
        pitchMap.put("3",eFlat);
        alterMap.put(Step.E.name(), eFlat.getAlter());

        Pitch fFlat = new Pitch();
        fFlat.setStep(Step.F);
        fFlat.setAlter(new BigDecimal("-1"));
        fFlat.setOctave(4);
        pitchMap.put("4",fFlat);
        alterMap.put(Step.F.name(), fFlat.getAlter());

        Pitch gFlat = new Pitch();
        gFlat.setStep(Step.G);
        gFlat.setAlter(new BigDecimal("-1"));
        gFlat.setOctave(4);
        pitchMap.put("5",gFlat);
        alterMap.put(Step.G.name(), gFlat.getAlter());

        Pitch aFlat = new Pitch();
        aFlat.setStep(Step.A);
        aFlat.setAlter(new BigDecimal("-1"));
        aFlat.setOctave(4);
        pitchMap.put("6",aFlat);
        alterMap.put(Step.A.name(), aFlat.getAlter());

        Pitch bFlat = new Pitch();
        bFlat.setStep(Step.B);
        bFlat.setAlter(new BigDecimal("-1"));
        bFlat.setOctave(4);
        pitchMap.put("7",bFlat);
        alterMap.put(Step.B.name(), bFlat.getAlter());
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }

    public static BigDecimal getAlter(String jpPitch){
        return alterMap.get(jpPitch);
    }
}
