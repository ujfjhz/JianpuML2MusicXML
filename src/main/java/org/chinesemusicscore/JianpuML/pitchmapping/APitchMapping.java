package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class APitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    public static Map<String, BigDecimal> alterMap = new HashMap<>();

    static {
        Pitch a = new Pitch();
        a.setStep(Step.A);
        a.setOctave(4);
        pitchMap.put("1",a);

        Pitch b = new Pitch();
        b.setStep(Step.B);
        b.setOctave(4);
        pitchMap.put("2",b);

        Pitch cSharp = new Pitch();
        cSharp.setStep(Step.C);
        cSharp.setAlter(new BigDecimal("1"));
        cSharp.setOctave(5);
        pitchMap.put("3",cSharp);
        alterMap.put(Step.C.name(), cSharp.getAlter());

        Pitch d = new Pitch();
        d.setStep(Step.D);
        d.setOctave(5);
        pitchMap.put("4",d);

        Pitch e = new Pitch();
        e.setStep(Step.E);
        e.setOctave(5);
        pitchMap.put("5",e);

        Pitch fShap = new Pitch();
        fShap.setStep(Step.F);
        fShap.setAlter(new BigDecimal("1"));
        fShap.setOctave(5);
        pitchMap.put("6",fShap);
        alterMap.put(Step.F.name(), fShap.getAlter());

        Pitch gShap = new Pitch();
        gShap.setStep(Step.G);
        gShap.setAlter(new BigDecimal("1"));
        gShap.setOctave(5);
        pitchMap.put("7",gShap);
        alterMap.put(Step.G.name(), gShap.getAlter());
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }

    public static BigDecimal getAlter(String jpPitch){
        return alterMap.get(jpPitch);
    }
}
