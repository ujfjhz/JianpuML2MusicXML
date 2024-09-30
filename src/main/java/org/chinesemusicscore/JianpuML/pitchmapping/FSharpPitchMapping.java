package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FSharpPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    static {
        Pitch fShap = new Pitch();
        fShap.setStep(Step.F);
        fShap.setAlter(new BigDecimal("1"));
        fShap.setOctave(4);
        pitchMap.put("1",fShap);

        Pitch gShap = new Pitch();
        gShap.setStep(Step.G);
        gShap.setAlter(new BigDecimal("1"));
        gShap.setOctave(4);
        pitchMap.put("2",gShap);

        Pitch aShap = new Pitch();
        aShap.setStep(Step.A);
        aShap.setAlter(new BigDecimal("1"));
        aShap.setOctave(4);
        pitchMap.put("3",aShap);

        Pitch b = new Pitch();
        b.setStep(Step.B);
        b.setOctave(4);
        pitchMap.put("4",b);

        Pitch cSharp = new Pitch();
        cSharp.setStep(Step.C);
        cSharp.setAlter(new BigDecimal("1"));
        cSharp.setOctave(5);
        pitchMap.put("5",cSharp);

        Pitch dSharp = new Pitch();
        dSharp.setStep(Step.D);
        dSharp.setAlter(new BigDecimal("1"));
        dSharp.setOctave(5);
        pitchMap.put("6",dSharp);

        Pitch eShap = new Pitch();
        eShap.setStep(Step.E);
        eShap.setAlter(new BigDecimal("1"));
        eShap.setOctave(5);
        pitchMap.put("7",eShap);
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }
}
