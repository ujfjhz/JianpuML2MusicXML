package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CSharpPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    public static Map<String, BigDecimal> alterMap = new HashMap<>();

    static {
        Pitch cSharp = new Pitch();
        cSharp.setStep(Step.C);
        cSharp.setAlter(new BigDecimal("1"));
        cSharp.setOctave(4);
        pitchMap.put("1",cSharp);
        alterMap.put(Step.C.name(), cSharp.getAlter());

        Pitch dSharp = new Pitch();
        dSharp.setStep(Step.D);
        dSharp.setAlter(new BigDecimal("1"));
        dSharp.setOctave(4);
        pitchMap.put("2",dSharp);
        alterMap.put(Step.D.name(), dSharp.getAlter());

        Pitch eShap = new Pitch();
        eShap.setStep(Step.E);
        eShap.setAlter(new BigDecimal("1"));
        eShap.setOctave(4);
        pitchMap.put("3",eShap);
        alterMap.put(Step.E.name(), eShap.getAlter());

        Pitch fSharp = new Pitch();
        fSharp.setStep(Step.F);
        fSharp.setAlter(new BigDecimal("1"));
        fSharp.setOctave(4);
        pitchMap.put("4",fSharp);
        alterMap.put(Step.F.name(), fSharp.getAlter());

        Pitch gSharp = new Pitch();
        gSharp.setStep(Step.G);
        gSharp.setAlter(new BigDecimal("1"));
        gSharp.setOctave(4);
        pitchMap.put("5",gSharp);
        alterMap.put(Step.G.name(), gSharp.getAlter());

        Pitch aSharp = new Pitch();
        aSharp.setStep(Step.A);
        aSharp.setAlter(new BigDecimal("1"));
        aSharp.setOctave(4);
        pitchMap.put("6",aSharp);
        alterMap.put(Step.A.name(), aSharp.getAlter());

        Pitch bSharp = new Pitch();
        bSharp.setStep(Step.B);
        bSharp.setAlter(new BigDecimal("1"));
        bSharp.setOctave(4);
        pitchMap.put("7",bSharp);
        alterMap.put(Step.B.name(), bSharp.getAlter());
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }

    public static BigDecimal getAlter(String jpPitch){
        return alterMap.get(jpPitch);
    }
}
