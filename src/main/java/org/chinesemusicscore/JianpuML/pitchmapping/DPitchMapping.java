package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    static {
        Pitch d = new Pitch();
        d.setStep(Step.D);
        d.setOctave(4);
        pitchMap.put("1",d);

        Pitch e = new Pitch();
        e.setStep(Step.E);
        e.setOctave(4);
        pitchMap.put("2",e);

        Pitch fShap = new Pitch();
        fShap.setStep(Step.F);
        fShap.setAlter(new BigDecimal("1"));
        fShap.setOctave(4);
        pitchMap.put("3",fShap);

        Pitch g = new Pitch();
        g.setStep(Step.G);
        g.setOctave(4);
        pitchMap.put("4",g);

        Pitch a = new Pitch();
        a.setStep(Step.A);
        a.setOctave(4);
        pitchMap.put("5",a);

        Pitch b = new Pitch();
        b.setStep(Step.B);
        b.setOctave(4);
        pitchMap.put("6",b);

        Pitch cSharp = new Pitch();
        cSharp.setStep(Step.C);
        cSharp.setAlter(new BigDecimal("1"));
        cSharp.setOctave(5);
        pitchMap.put("7",cSharp);
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }
}
