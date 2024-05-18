package org.chinesemusicscore.JianpuML.util;

import org.audiveris.proxymusic.Pitch;
import org.chinesemusicscore.JianpuML.pitchmapping.*;

public class JianpuPitchUtil {
    public static Pitch getPitch(String jpKey, String jpPitch){
        Pitch pitch = null;

        if("D".equalsIgnoreCase(jpKey)){
            pitch = DPitchMapping.getPitch(jpPitch);
        }else if("G".equalsIgnoreCase(jpKey)){
            pitch = GPitchMapping.getPitch(jpPitch);
        }else if("C".equalsIgnoreCase(jpKey)){
            pitch = CPitchMapping.getPitch(jpPitch);
        }else if("F".equalsIgnoreCase(jpKey)){
            pitch = FPitchMapping.getPitch(jpPitch);
        }else if("Bb".equalsIgnoreCase(jpKey)){
            pitch = BbPitchMapping.getPitch(jpPitch);
        }

        return pitch;
    }
}
