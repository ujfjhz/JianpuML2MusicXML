package org.chinesemusicscore.JianpuML;
import org.apache.logging.log4j.util.Strings;
import org.audiveris.proxymusic.*;
import org.audiveris.proxymusic.util.Marshalling;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class JianpuMLParser{
    private Map<String,String> getMetaData(String jianpuML){
        Map<String,String> metaData = new HashMap<>();

        String[] lines = jianpuML.split("\n");
        for (String line : lines) {
            if (line.contains(":")) {
                String[] split = line.split(":");
                metaData.put(split[0].trim(), split[1].trim());
            }
        }

        return metaData;
    }

    private Work createWork(Map<String,String> metaData){
        Work work = new Work();
        work.setWorkTitle(metaData.getOrDefault("Title", "Title"));
        return work;
    }

    private Identification createIdentification(Map<String,String> metaData){
        Identification identification = new Identification();

        TypedText composer = new TypedText();
        composer.setType("composer");
        composer.setValue(metaData.getOrDefault("Composer", "Composer"));
        identification.getCreator().add(composer);

        TypedText arranger = new TypedText();
        arranger.setType("arranger");
        arranger.setValue("ChineseMusicScore");
        identification.getCreator().add(arranger);

        TypedText right = new TypedText();
        right.setValue("by ChineseMusicScore(MIT License)");
        identification.getRights().add(right);

        return identification;
    }

    private Attributes createAttributes(Map<String,String> metaData){
        Attributes attributes = new Attributes();
        ObjectFactory factory = new ObjectFactory();
        attributes.setDivisions(new BigDecimal("4"));

        Key key = new Key();
        key.setFifths(BigInteger.valueOf(2));//todo hardcode
        attributes.getKey().add(key);

        Time time = new Time();
        String timeSignature = metaData.get("TimeSignature");
        String[] split = timeSignature.split("/");
        JAXBElement<String> timeBeats = factory.createTimeBeats(split[0]);
        JAXBElement<String> timeBeatType = factory.createTimeBeatType(split[1]);
        time.getTimeSignature().add(timeBeats);
        time.getTimeSignature().add(timeBeatType);
        attributes.getTime().add(time);

        Clef clef = new Clef();
        clef.setSign(ClefSign.G);
        clef.setLine(BigInteger.valueOf(2));
        attributes.getClef().add(clef);

        return attributes;
    }

    private Direction createDirection(Map<String,String> metaData){
        if(!metaData.containsKey("Tempo")){
            return null;
        }
        String tempo = metaData.get("Tempo");

        Direction direction = new Direction();
        direction.setPlacement(AboveBelow.ABOVE);

        DirectionType directionType = new DirectionType();
        Metronome metronome = new Metronome();

        metronome.getBeatUnit().add("quarter");
        PerMinute perMinute = new PerMinute();
        perMinute.setValue(tempo);
        metronome.setPerMinute(perMinute);
        directionType.setMetronome(metronome);

        direction.getDirectionType().add(directionType);

        Sound sound = new Sound();
        sound.setTempo(BigDecimal.valueOf(Long.parseLong(tempo)));
        direction.setSound(sound);

        return direction;
    }

    public ScorePartwise parseJianpuML(String jianpuML) {
        Map<String, String> metaData = getMetaData(jianpuML);

        ScorePartwise scorePartwise = new ScorePartwise();
        scorePartwise.setWork(createWork(metaData));
        scorePartwise.setIdentification(createIdentification(metaData));

        Attributes attributes = createAttributes(metaData);

        Direction direction = createDirection(metaData);

        PartList partList = new PartList();
        scorePartwise.setPartList(partList);

        // handle each part
        ScorePart scorePart = new ScorePart();
        scorePart.setId("P1");
        PartName partName = new PartName();
        partName.setValue("General");
        scorePart.setPartName(partName);
        partList.getPartGroupOrScorePart().add(scorePart);

        ScorePartwise.Part part = new ScorePartwise.Part();
        part.setId(scorePart);

        int measureNo = 1;

        ScorePartwise.Part.Measure lastMeasure = null;
        String[] lines = jianpuML.split("\n");
        for (String line : lines) {
            if (Strings.isBlank(line) || line.contains(":")) {
                continue;
            }

            String[] measureStrs = line.split("\\|");
            for (String measureStr : measureStrs) {
                if(Strings.isBlank(measureStr)){
                    continue;
                }

                String[] measureSplit = measureStr.split("&");
                ScorePartwise.Part.Measure measure = new ScorePartwise.Part.Measure();
                measure.setNumber(measureNo + "");
                if (measureNo == 1) {
                    measure.getNoteOrBackupOrForward().add(attributes);
                    measure.getNoteOrBackupOrForward().add(direction);
                }
                lastMeasure = measure;

                if(measureSplit.length>0) {
                    String[] notes = measureSplit[0].trim().split("\\s+"); // only support one line
                    for (String noteString : notes) {
                        Note note = convertJianpuNote(noteString, metaData.getOrDefault("DefaultNoteDivide", "4"));
                        if (note != null) {
                            measure.getNoteOrBackupOrForward().add(note);
                        }
                    }
                }

                part.getMeasure().add(measure);
                measureNo++;
            }
        }

        if(lastMeasure!=null) {
            Barline barline = new Barline();
            barline.setLocation(RightLeftMiddle.RIGHT);

            BarStyleColor barStyleColor = new BarStyleColor();
            barStyleColor.setValue(BarStyle.LIGHT_HEAVY);
            barline.setBarStyle(barStyleColor);

            lastMeasure.getNoteOrBackupOrForward().add(barline);
        }

        scorePartwise.getPart().add(part);

        return scorePartwise;
    }

    private Note convertJianpuNote(String jianpuNote, String defaultNoteDivide) {
        if (jianpuNote.isEmpty()) {
            return null;
        }

        Note note = new Note();

        double duration = 4; // default duration for quarter
        String jianpuPitch = jianpuNote;
        if(Strings.isBlank(defaultNoteDivide)) {
            defaultNoteDivide = "4";
        }
        if (jianpuNote.contains("/")) {
            String[] parts = jianpuNote.split("/");
            jianpuPitch = parts[0];
            defaultNoteDivide = parts[1];
            if(defaultNoteDivide.contains(".")){
                EmptyPlacement dot = new EmptyPlacement();
                note.getDot().add(dot);
                defaultNoteDivide = defaultNoteDivide.replaceAll("\\.","");
            }
            duration = 16 / Integer.parseInt(defaultNoteDivide);
        }
        note.setDuration(BigDecimal.valueOf(duration));
        NoteType noteType = NoteTypeUtil.getNoteType(defaultNoteDivide);
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


    public static void main(String[] args) throws Exception {
        File inputfile = new File("input.jml");
        String jianpu = new String(Files.readAllBytes(inputfile.toPath()));

        JianpuMLParser jianpuMLParser = new JianpuMLParser();
        ScorePartwise scorePartwise = jianpuMLParser.parseJianpuML(jianpu);

        File file = new File("output.musicxml");
        OutputStream os = new FileOutputStream(file);
        Marshalling.marshal(scorePartwise, os, false, 4);

        System.out.println("MusicXML file has been created: " + file.getAbsolutePath());
    }
}
