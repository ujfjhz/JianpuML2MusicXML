package org.chinesemusicscore.JianpuML;

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

        NoteType thirtytwoth = new NoteType();
        thirtytwoth.setValue("32th");
        noteTypeMap.put("32", thirtytwoth);
    }

    public static NoteType getNoteType(String divide){
        if(Strings.isEmpty(divide) || !noteTypeMap.containsKey(divide)){
            return noteTypeMap.get("4");
        }else {
            return noteTypeMap.get(divide);
        }
    }
}
