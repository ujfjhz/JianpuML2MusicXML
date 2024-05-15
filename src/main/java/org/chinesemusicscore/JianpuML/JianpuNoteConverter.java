package org.chinesemusicscore.JianpuML;

import org.apache.logging.log4j.util.Strings;
import org.audiveris.proxymusic.*;
import org.chinesemusicscore.JianpuML.pitchmapping.DPitchMapping;
import org.chinesemusicscore.JianpuML.util.NoteTypeUtil;

import java.lang.String;
import java.math.BigDecimal;

public class JianpuNoteConverter {
    public static Note convertJianpuNote(String jianpuNote, String defaultNoteDuration) {
        if (jianpuNote.isEmpty()) {
            return null;
        }

        Note note = new Note();

        String jianpuPitch = jianpuNote;
        String noteDuration = defaultNoteDuration;
        if (jianpuNote.contains("/")) {
            String[] parts = jianpuNote.split("/");
            jianpuPitch = parts[0];
            noteDuration = parts[1];
            if(noteDuration.contains(".")){
                EmptyPlacement dot = new EmptyPlacement();
                note.getDot().add(dot);
                noteDuration = noteDuration.replaceAll("\\.","");
            }
        }
        double duration = 16 / Integer.parseInt(noteDuration);
        note.setDuration(BigDecimal.valueOf(duration));
        NoteType noteType = NoteTypeUtil.getNoteType(noteDuration);
        note.setType(noteType);

        //set pitch
        String[] jpPitchSplit = jianpuPitch.split(",");
        jianpuPitch = jpPitchSplit[0];
        if(jianpuPitch.equals("0")){
            Rest rest = new Rest();
            note.setRest(rest);
        }else {
            Pitch pitch = new Pitch();

            if (jianpuPitch.matches("\\d[#b]?")) {
                if (jianpuPitch.contains("#")) {
                    pitch.setAlter(new BigDecimal("1"));
                } else if (jianpuPitch.contains("b")) {
                    pitch.setAlter(new BigDecimal("-1"));
                }

                jianpuPitch = jianpuPitch.replaceFirst("[#b]", "");
            }

            int octaveDiff = 0;
            if (jianpuPitch.endsWith(".")) {
                octaveDiff = (jianpuPitch.length() - 1);
                jianpuPitch = jianpuPitch.substring(0, 1);
            } else if (jianpuPitch.startsWith(".")) {
                octaveDiff = - (jianpuPitch.length() - 1);
                jianpuPitch = jianpuPitch.substring(jianpuPitch.length() - 1);
            }

            Pitch stdPitch = DPitchMapping.getPitch(jianpuPitch);
            pitch.setStep(stdPitch.getStep());
            pitch.setOctave(stdPitch.getOctave()+octaveDiff);
            if (stdPitch.getAlter() != null) {
                if (pitch.getAlter() == null) {
                    pitch.setAlter(stdPitch.getAlter());
                } else {
                    pitch.getAlter().add(stdPitch.getAlter());
                }
            }

            note.setPitch(pitch);
        }

        return note;
    }
}
