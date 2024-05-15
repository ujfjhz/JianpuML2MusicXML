package org.chinesemusicscore.JianpuML;
import org.apache.logging.log4j.util.Strings;
import org.audiveris.proxymusic.*;
import org.audiveris.proxymusic.util.Marshalling;
import org.chinesemusicscore.JianpuML.pitchmapping.DPitchMapping;
import org.chinesemusicscore.JianpuML.util.NoteTypeUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.String;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Map;

import static org.chinesemusicscore.JianpuML.JianpuNoteConverter.convertJianpuNote;
import static org.chinesemusicscore.JianpuML.util.MetaUtil.*;

public class JianpuMLParser{
    public ScorePartwise parseJianpuML(String jianpuML) {
        Map<String, String> metaData = getMetaData(jianpuML);
        String defaultDuration = metaData.getOrDefault("DefaultDuration", "4");

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
                        Note note = convertJianpuNote(noteString, defaultDuration);
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
