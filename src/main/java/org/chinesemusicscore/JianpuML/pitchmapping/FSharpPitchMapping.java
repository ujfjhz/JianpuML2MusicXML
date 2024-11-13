package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FSharpPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    public static Map<String, BigDecimal> alterMap = new HashMap<>();

    static {
        Pitch fShap = new Pitch();
        fShap.setStep(Step.F);
        fShap.setAlter(new BigDecimal("1"));
        fShap.setOctave(4);
        pitchMap.put("1",fShap);
        alterMap.put(Step.F.name(), fShap.getAlter());

        Pitch gShap = new Pitch();
        gShap.setStep(Step.G);
        gShap.setAlter(new BigDecimal("1"));
        gShap.setOctave(4);
        pitchMap.put("2",gShap);
        alterMap.put(Step.G.name(), gShap.getAlter());

        Pitch aShap = new Pitch();
        aShap.setStep(Step.A);
        aShap.setAlter(new BigDecimal("1"));
        aShap.setOctave(4);
        pitchMap.put("3",aShap);
        alterMap.put(Step.A.name(), aShap.getAlter());

        Pitch b = new Pitch();
        b.setStep(Step.B);
        b.setOctave(4);
        pitchMap.put("4",b);

        Pitch cSharp = new Pitch();
        cSharp.setStep(Step.C);
        cSharp.setAlter(new BigDecimal("1"));
        cSharp.setOctave(5);
        pitchMap.put("5",cSharp);
        alterMap.put(Step.C.name(), cSharp.getAlter());

        Pitch dSharp = new Pitch();
        dSharp.setStep(Step.D);
        dSharp.setAlter(new BigDecimal("1"));
        dSharp.setOctave(5);
        pitchMap.put("6",dSharp);
        alterMap.put(Step.D.name(), dSharp.getAlter());

        Pitch eShap = new Pitch();
        eShap.setStep(Step.E);
        eShap.setAlter(new BigDecimal("1"));
        eShap.setOctave(5);
        pitchMap.put("7",eShap);
        alterMap.put(Step.E.name(), eShap.getAlter());
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }

    public static BigDecimal getAlter(String jpPitch){
        return alterMap.get(jpPitch);
    }
}
