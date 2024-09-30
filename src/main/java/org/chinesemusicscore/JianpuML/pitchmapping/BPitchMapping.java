package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    static {
        Pitch b = new Pitch();
        b.setStep(Step.B);
        b.setOctave(4);
        pitchMap.put("1",b);

        Pitch cSharp = new Pitch();
        cSharp.setStep(Step.C);
        cSharp.setAlter(new BigDecimal("1"));
        cSharp.setOctave(5);
        pitchMap.put("2",cSharp);

        Pitch dSharp = new Pitch();
        dSharp.setStep(Step.D);
        dSharp.setAlter(new BigDecimal("1"));
        dSharp.setOctave(5);
        pitchMap.put("3",dSharp);

        Pitch e = new Pitch();
        e.setStep(Step.E);
        e.setOctave(5);
        pitchMap.put("4",e);

        Pitch fShap = new Pitch();
        fShap.setStep(Step.F);
        fShap.setAlter(new BigDecimal("1"));
        fShap.setOctave(5);
        pitchMap.put("5",fShap);

        Pitch gShap = new Pitch();
        gShap.setStep(Step.G);
        gShap.setAlter(new BigDecimal("1"));
        gShap.setOctave(5);
        pitchMap.put("6",gShap);

        Pitch aShap = new Pitch();
        aShap.setStep(Step.A);
        aShap.setAlter(new BigDecimal("1"));
        aShap.setOctave(5);
        pitchMap.put("7",aShap);
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }
}
