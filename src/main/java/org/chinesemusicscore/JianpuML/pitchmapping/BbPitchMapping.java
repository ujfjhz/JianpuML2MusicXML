package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class BbPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    public static Map<String, BigDecimal> alterMap = new HashMap<>();

    static {
        Pitch b = new Pitch();
        b.setStep(Step.B);
        b.setOctave(3);
        b.setAlter(new BigDecimal("-1"));
        pitchMap.put("1",b);
        alterMap.put(Step.B.name(), b.getAlter());

        Pitch c = new Pitch();
        c.setStep(Step.C);
        c.setOctave(4);
        pitchMap.put("2",c);

        Pitch d = new Pitch();
        d.setStep(Step.D);
        d.setOctave(4);
        pitchMap.put("3",d);

        Pitch e = new Pitch();
        e.setStep(Step.E);
        e.setOctave(4);
        e.setAlter(new BigDecimal("-1"));
        pitchMap.put("4",e);
        alterMap.put(Step.E.name(), e.getAlter());

        Pitch f = new Pitch();
        f.setStep(Step.F);
        f.setOctave(4);
        pitchMap.put("5",f);

        Pitch g = new Pitch();
        g.setStep(Step.G);
        g.setOctave(4);
        pitchMap.put("6",g);

        Pitch a = new Pitch();
        a.setStep(Step.A);
        a.setOctave(4);
        pitchMap.put("7",a);


    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }

    public static BigDecimal getAlter(String jpPitch){
        return alterMap.get(jpPitch);
    }
}
