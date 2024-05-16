package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    static {
        Pitch f = new Pitch();
        f.setStep(Step.F);
        f.setOctave(4);
        pitchMap.put("1",f);

        Pitch g = new Pitch();
        g.setStep(Step.G);
        g.setOctave(4);
        pitchMap.put("2",g);

        Pitch a = new Pitch();
        a.setStep(Step.A);
        a.setOctave(4);
        pitchMap.put("3",a);

        Pitch b = new Pitch();
        b.setStep(Step.B);
        b.setOctave(4);
        b.setAlter(new BigDecimal("-1"));
        pitchMap.put("4",b);

        Pitch c = new Pitch();
        c.setStep(Step.C);
        c.setOctave(5);
        pitchMap.put("5",c);

        Pitch d = new Pitch();
        d.setStep(Step.D);
        d.setOctave(5);
        pitchMap.put("6",d);

        Pitch e = new Pitch();
        e.setStep(Step.E);
        e.setOctave(5);
        pitchMap.put("7",e);

    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }
}
