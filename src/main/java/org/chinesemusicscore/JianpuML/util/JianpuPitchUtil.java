package org.chinesemusicscore.JianpuML.util;

import org.audiveris.proxymusic.Pitch;
import org.chinesemusicscore.JianpuML.pitchmapping.*;

public class JianpuPitchUtil {
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
    public static Pitch getPitch(String jpPitch){
        Pitch pitch = CPitchMapping.getPitch(jpPitch);
        return pitch;
    }
}
