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

        // 添加版权声明
        TypedText right = new TypedText();
        right.setValue("by ChineseMusicScore(MIT License)");
        identification.getRights().add(right);

        return identification;
    }

    private Attributes createAttributes(Map<String,String> metaData){
        Attributes attributes = new Attributes();
        ObjectFactory factory = new ObjectFactory();
        // 设置 divisions
        attributes.setDivisions(new BigDecimal("4"));

        // 设置 Key
        Key key = new Key();
        key.setFifths(BigInteger.valueOf(2));//todo hardcode
        attributes.getKey().add(key);

        // 设置 Time
        Time time = new Time();
        String timeSignature = metaData.get("TimeSignature");
        String[] split = timeSignature.split("/");
        JAXBElement<String> timeBeats = factory.createTimeBeats(split[0]);
        JAXBElement<String> timeBeatType = factory.createTimeBeatType(split[1]);
        time.getTimeSignature().add(timeBeats);
        time.getTimeSignature().add(timeBeatType);
        attributes.getTime().add(time);

        // 设置 Clef
        Clef clef = new Clef();
        clef.setSign(ClefSign.G);
        clef.setLine(BigInteger.valueOf(2));
        attributes.getClef().add(clef);

        return attributes;
    }

    public ScorePartwise parseJianpuML(String jianpuML) {
        Map<String, String> metaData = getMetaData(jianpuML);

        ScorePartwise scorePartwise = new ScorePartwise();
        scorePartwise.setWork(createWork(metaData));
        scorePartwise.setIdentification(createIdentification(metaData));

        // 创建 Attributes 对象
        Attributes attributes = createAttributes(metaData);

        // 配置部件列表和乐队部分
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

                ScorePartwise.Part.Measure measure = new ScorePartwise.Part.Measure();
                measure.setNumber(measureNo+"");
                if(measureNo==1){
                    measure.getNoteOrBackupOrForward().add(attributes);
                }

                String[] notes = measureStr.trim().split("\\s+");
                for (String noteString : notes) {
                    Note note = convertJianpuNote(noteString);
                    if (note != null) {
                        measure.getNoteOrBackupOrForward().add(note);
                    }
                }

                part.getMeasure().add(measure);
                measureNo++;
            }
        }

        scorePartwise.getPart().add(part);

        return scorePartwise;
    }

    private Note convertJianpuNote(String jianpuNote) {
        if (jianpuNote.isEmpty()) {
            return null;
        }

        Note note = new Note();

        double duration = 4; // default duration for quarter
        String jianpuPitch = jianpuNote;
        String divide = "4";
        if (jianpuNote.contains("/")) {
            String[] parts = jianpuNote.split("/");
            jianpuPitch = parts[0];
            divide = parts[1];
            if(divide.contains(".")){
                EmptyPlacement dot = new EmptyPlacement();
                note.getDot().add(dot);
                divide = divide.replaceAll("\\.","");
            }
            duration = 16 / Integer.parseInt(divide);
        }
        note.setDuration(BigDecimal.valueOf(duration));
        NoteType noteType = NoteTypeUtil.getNoteType(divide);
        note.setType(noteType);

        //set pitch
        Pitch pitch = new Pitch();

        if (jianpuPitch.matches("\\d[#b]?")) {
            if (jianpuPitch.contains("#")) {
                pitch.setAlter(new BigDecimal("1"));
            } else if (jianpuPitch.contains("b")) {
                pitch.setAlter(new BigDecimal("-1"));
            }

            jianpuPitch = jianpuPitch.replaceFirst("[#b]","");
        }

        int octave = 4;
        if (jianpuPitch.endsWith(".")) {
            octave = octave + (jianpuPitch.length()-1);
            jianpuPitch = jianpuPitch.substring(0,1);
        } else if (jianpuPitch.startsWith(".")) {
            octave = octave - (jianpuPitch.length()-1);
            jianpuPitch = jianpuPitch.substring(jianpuPitch.length()-1);
        }
        pitch.setOctave(octave);

        Pitch stdPitch = DPitchMapping.getPitch(jianpuPitch);
        pitch.setStep(stdPitch.getStep());
        if(stdPitch.getAlter()!=null){
            if(pitch.getAlter()==null){
                pitch.setAlter(stdPitch.getAlter());
            }else {
                pitch.getAlter().add(stdPitch.getAlter());
            }
        }

        note.setPitch(pitch);

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
