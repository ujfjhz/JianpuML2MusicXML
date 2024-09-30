package org.chinesemusicscore.JianpuML.pitchmapping;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class EbPitchMapping {
    private static Map<String, Pitch> pitchMap = new HashMap<>();

    static {
        Pitch eFlat = new Pitch();
        eFlat.setStep(Step.E);
        eFlat.setAlter(new BigDecimal("-1"));
        eFlat.setOctave(4);
        pitchMap.put("1",eFlat);

        Pitch f = new Pitch();
        f.setStep(Step.F);
        f.setOctave(4);
        pitchMap.put("2",f);

        Pitch g = new Pitch();
        g.setStep(Step.G);
        g.setOctave(4);
        pitchMap.put("3",g);

        Pitch aFlat = new Pitch();
        aFlat.setStep(Step.A);
        aFlat.setAlter(new BigDecimal("-1"));
        aFlat.setOctave(4);
        pitchMap.put("4",aFlat);

        Pitch bFlat = new Pitch();
        bFlat.setStep(Step.B);
        bFlat.setAlter(new BigDecimal("-1"));
        bFlat.setOctave(4);
        pitchMap.put("5",bFlat);

        Pitch c = new Pitch();
        c.setStep(Step.C);
        c.setOctave(5);
        pitchMap.put("6",c);

        Pitch d = new Pitch();
        d.setStep(Step.D);
        d.setOctave(5);
        pitchMap.put("7",d);
    }

    public static Pitch getPitch(String jpPitch){
        return pitchMap.get(jpPitch);
    }
}
