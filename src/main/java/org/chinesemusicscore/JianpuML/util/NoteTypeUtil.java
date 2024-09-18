package org.chinesemusicscore.JianpuML.util;

import org.apache.logging.log4j.util.Strings;
import org.audiveris.proxymusic.NoteType;

import java.util.HashMap;
import java.util.Map;

public class NoteTypeUtil {
    private static Map<String, NoteType> noteTypeMap = new HashMap<>();

    static {
        NoteType whole = new NoteType();
        whole.setValue("whole");
        noteTypeMap.put("1", whole);

        NoteType half = new NoteType();
        half.setValue("half");
        noteTypeMap.put("2", half);

        NoteType quarter = new NoteType();
        quarter.setValue("quarter");
        noteTypeMap.put("4", quarter);

        NoteType eighth = new NoteType();
        eighth.setValue("eighth");
        noteTypeMap.put("8", eighth);

        NoteType sixteenth = new NoteType();
        sixteenth.setValue("16th");
        noteTypeMap.put("16", sixteenth);

        NoteType thirtysecond = new NoteType();
        thirtysecond.setValue("32nd");
        noteTypeMap.put("32", thirtysecond);

        NoteType sixtyfourth = new NoteType();
        sixtyfourth.setValue("64th");
        noteTypeMap.put("64", sixtyfourth);
    }

    public static NoteType getNoteType(String divide){
        if(Strings.isEmpty(divide) || !noteTypeMap.containsKey(divide)){
            return noteTypeMap.get("4");
        }else {
            return noteTypeMap.get(divide);
        }
    }
}
