package org.chinesemusicscore.JianpuML.util;

import org.audiveris.proxymusic.Pitch;
import org.audiveris.proxymusic.Step;
import org.chinesemusicscore.JianpuML.pitchmapping.*;

public class JianpuPitchUtil {
    /**
     * get staff pitch from jpPitch
     * @param jpKey
     * @param jpPitch
     * @return
     */
    public static Pitch getPitch(String jpKey, String jpPitch){
        Pitch pitch = null;

        if("C".equalsIgnoreCase(jpKey)){
            pitch = CPitchMapping.getPitch(jpPitch);
        }else if("G".equalsIgnoreCase(jpKey)){
            pitch = GPitchMapping.getPitch(jpPitch);
        }else if("D".equalsIgnoreCase(jpKey)){
            pitch = DPitchMapping.getPitch(jpPitch);
        }else if("A".equalsIgnoreCase(jpKey)){
            pitch = APitchMapping.getPitch(jpPitch);
        }else if("E".equalsIgnoreCase(jpKey)){
            pitch = EPitchMapping.getPitch(jpPitch);
        }else if("B".equalsIgnoreCase(jpKey)){
            pitch = BPitchMapping.getPitch(jpPitch);
        }else if("F#".equalsIgnoreCase(jpKey)){
            pitch = FSharpPitchMapping.getPitch(jpPitch);
        }else if("C#".equalsIgnoreCase(jpKey)){
            pitch = CSharpPitchMapping.getPitch(jpPitch);
        }else if("F".equalsIgnoreCase(jpKey)){
            pitch = FPitchMapping.getPitch(jpPitch);
        }else if("Bb".equalsIgnoreCase(jpKey)){
            pitch = BbPitchMapping.getPitch(jpPitch);
        }else if("Eb".equalsIgnoreCase(jpKey)){
            pitch = EbPitchMapping.getPitch(jpPitch);
        }else if("Ab".equalsIgnoreCase(jpKey)){
            pitch = AbPitchMapping.getPitch(jpPitch);
        }else if("Db".equalsIgnoreCase(jpKey)){
            pitch = DbPitchMapping.getPitch(jpPitch);
        }else if("Gb".equalsIgnoreCase(jpKey)){
            pitch = GbPitchMapping.getPitch(jpPitch);
        }else if("Cb".equalsIgnoreCase(jpKey)){
            pitch = CbPitchMapping.getPitch(jpPitch);
        }

        return pitch;
    }

    /**
     * for staff notation in number form
     * @param pitch
     * @return
     */
    public static Pitch getStaffPitch(String jpKey, String jpPitch){
        Pitch pitch = new Pitch();
        pitch.setStep(Step.fromValue(jpPitch.toUpperCase()));
        pitch.setOctave(4);

        if("C".equalsIgnoreCase(jpKey)){
            pitch.setAlter(CPitchMapping.getAlter(jpPitch));
        }else if("G".equalsIgnoreCase(jpKey)){
            pitch.setAlter(GPitchMapping.getAlter(jpPitch));
        }else if("D".equalsIgnoreCase(jpKey)){
            pitch.setAlter(DPitchMapping.getAlter(jpPitch));
        }else if("A".equalsIgnoreCase(jpKey)){
            pitch.setAlter(APitchMapping.getAlter(jpPitch));
        }else if("E".equalsIgnoreCase(jpKey)){
            pitch.setAlter(EPitchMapping.getAlter(jpPitch));
        }else if("B".equalsIgnoreCase(jpKey)){
            pitch.setAlter(BPitchMapping.getAlter(jpPitch));
        }else if("F#".equalsIgnoreCase(jpKey)){
            pitch.setAlter(FSharpPitchMapping.getAlter(jpPitch));
        }else if("C#".equalsIgnoreCase(jpKey)){
            pitch.setAlter(CSharpPitchMapping.getAlter(jpPitch));
        }else if("F".equalsIgnoreCase(jpKey)){
            pitch.setAlter(FPitchMapping.getAlter(jpPitch));
        }else if("Bb".equalsIgnoreCase(jpKey)){
            pitch.setAlter(BbPitchMapping.getAlter(jpPitch));
        }else if("Eb".equalsIgnoreCase(jpKey)){
            pitch.setAlter(EbPitchMapping.getAlter(jpPitch));
        }else if("Ab".equalsIgnoreCase(jpKey)){
            pitch.setAlter(AbPitchMapping.getAlter(jpPitch));
        }else if("Db".equalsIgnoreCase(jpKey)){
            pitch.setAlter(DbPitchMapping.getAlter(jpPitch));
        }else if("Gb".equalsIgnoreCase(jpKey)){
            pitch.setAlter(GbPitchMapping.getAlter(jpPitch));
        }else if("Cb".equalsIgnoreCase(jpKey)){
            pitch.setAlter(CbPitchMapping.getAlter(jpPitch));
        }

        return pitch;
    }
}
