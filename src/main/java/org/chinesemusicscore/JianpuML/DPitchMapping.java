package org.chinesemusicscore.JianpuML;

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
        pitchMap.put("1",d);

        Pitch e = new Pitch();
        e.setStep(Step.E);
        pitchMap.put("2",e);

        Pitch fShap = new Pitch();
        fShap.setStep(Step.F);
        fShap.setAlter(new BigDecimal("1"));
        pitchMap.put("3",fShap);

        Pitch g = new Pitch();
        g.setStep(Step.G);
        pitchMap.put("4",g);

        Pitch a = new Pitch();
        a.setStep(Step.A);
        pitchMap.put("5",a);

        Pitch b = new Pitch();
        b.setStep(Step.B);
        pitchMap.put("6",b);

        Pitch cSharp = new Pitch();
        cSharp.setStep(Step.C);
        cSharp.setAlter(new BigDecimal("1"));
        pitchMap.put("7",cSharp);
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }
}
