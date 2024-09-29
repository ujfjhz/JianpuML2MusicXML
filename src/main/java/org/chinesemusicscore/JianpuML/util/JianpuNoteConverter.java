package org.chinesemusicscore.JianpuML.util;

import org.audiveris.proxymusic.*;

import java.lang.String;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JianpuNoteConverter {
    public static List<Note> convertJianpuNote(String jpKey, String jianpuNote, String defaultNoteDuration) {
        List<Note> notes = new ArrayList<>();
        if (jianpuNote.isEmpty()) {
            return notes;
        }

        String jianpuPitch = jianpuNote;
        String noteDuration = defaultNoteDuration;
        Boolean dotted = false;
        if (jianpuNote.contains("/")) {
            String[] parts = jianpuNote.split("/");
            jianpuPitch = parts[0];
            noteDuration = parts[1];
            if(noteDuration.contains(".")){
                dotted = true;
                noteDuration = noteDuration.replaceAll("\\.","");
            }
        }
        String divide = NoteTypeUtil.getDivide(noteDuration);
        double duration = 16.0 / Integer.parseInt(divide);

        String[] jpPitchSplit = jianpuPitch.split(",");
        for(int i=0;i<jpPitchSplit.length;i++){
            Note tmpNote = new Note();

            tmpNote.setDuration(BigDecimal.valueOf(duration));
            NoteType noteType = NoteTypeUtil.getNoteType(divide);
            tmpNote.setType(noteType);

            if(dotted){
                EmptyPlacement dot = new EmptyPlacement();
                tmpNote.getDot().add(dot);
            }

            if(jpPitchSplit[i].equals("0")){
                Rest rest = new Rest();
                tmpNote.setRest(rest);
            }else {
                Pitch pitch = convert(jpKey, jpPitchSplit[i]);
                tmpNote.setPitch(pitch);
                if(i>0){
                    tmpNote.setChord(new Empty());
                }
            }

            notes.add(tmpNote);
        }

        return notes;
    }

    private static Pitch convert(String jpKey, String jianpuPitch){
        Pitch pitch = new Pitch();

        if (jianpuPitch.matches("\\.*\\d\\.*[#b]?")) {
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

        Pitch stdPitch = JianpuPitchUtil.getPitch(jpKey, jianpuPitch);

        if(stdPitch!=null) {
            pitch.setStep(stdPitch.getStep());
        }else {
            throw new RuntimeException("invalid pitch "+jianpuPitch);
        }
        pitch.setOctave(stdPitch.getOctave()+octaveDiff);
        if (stdPitch.getAlter() != null) {
            if (pitch.getAlter() == null) {
                pitch.setAlter(stdPitch.getAlter());
            } else {
                pitch.getAlter().add(stdPitch.getAlter());
            }
        }

        return pitch;
    }
}
