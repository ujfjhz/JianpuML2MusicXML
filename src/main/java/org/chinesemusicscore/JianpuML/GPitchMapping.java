package org.chinesemusicscore.JianpuML;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class GPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    static {
        Pitch g = new Pitch();
        g.setStep(Step.G);
        pitchMap.put("1",g);

        Pitch a = new Pitch();
        a.setStep(Step.A);
        pitchMap.put("2",a);

        Pitch b = new Pitch();
        b.setStep(Step.B);
        pitchMap.put("3",b);

        Pitch c = new Pitch();
        c.setStep(Step.C);
        pitchMap.put("4",c);

        Pitch d = new Pitch();
        d.setStep(Step.D);
        pitchMap.put("5",d);

        Pitch e = new Pitch();
        e.setStep(Step.E);
        pitchMap.put("6",e);

        Pitch fShap = new Pitch();
        fShap.setStep(Step.F);
        fShap.setAlter(new BigDecimal("1"));
        pitchMap.put("7",fShap);
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }
}
