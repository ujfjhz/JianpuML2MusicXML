package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CSharpPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    static {
        Pitch cSharp = new Pitch();
        cSharp.setStep(Step.C);
        cSharp.setAlter(new BigDecimal("1"));
        cSharp.setOctave(4);
        pitchMap.put("1",cSharp);

        Pitch dSharp = new Pitch();
        dSharp.setStep(Step.D);
        dSharp.setAlter(new BigDecimal("1"));
        dSharp.setOctave(4);
        pitchMap.put("2",dSharp);

        Pitch eShap = new Pitch();
        eShap.setStep(Step.E);
        eShap.setAlter(new BigDecimal("1"));
        eShap.setOctave(4);
        pitchMap.put("3",eShap);

        Pitch fSharp = new Pitch();
        fSharp.setStep(Step.F);
        fSharp.setAlter(new BigDecimal("1"));
        fSharp.setOctave(4);
        pitchMap.put("4",fSharp);

        Pitch gSharp = new Pitch();
        gSharp.setStep(Step.G);
        gSharp.setAlter(new BigDecimal("1"));
        gSharp.setOctave(4);
        pitchMap.put("5",gSharp);

        Pitch aSharp = new Pitch();
        aSharp.setStep(Step.A);
        aSharp.setAlter(new BigDecimal("1"));
        aSharp.setOctave(4);
        pitchMap.put("6",aSharp);

        Pitch bSharp = new Pitch();
        bSharp.setStep(Step.B);
        bSharp.setAlter(new BigDecimal("1"));
        bSharp.setOctave(4);
        pitchMap.put("7",bSharp);
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }
}
