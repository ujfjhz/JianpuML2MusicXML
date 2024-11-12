package org.chinesemusicscore.JianpuML;
import org.apache.logging.log4j.util.Strings;
import org.audiveris.proxymusic.*;
import org.chinesemusicscore.JianpuML.notation.NotationHelper;
import org.chinesemusicscore.JianpuML.property.ScorePropertyHelper;
import java.lang.String;
import java.util.List;
import java.util.Map;

import static org.chinesemusicscore.JianpuML.util.JianpuNoteConverter.convertJianpuNote;
import static org.chinesemusicscore.JianpuML.util.MetaUtil.*;

public class JianpuMLParser{
    public ScorePartwise parseJianpuML(String jianpuML) {
        jianpuML = jianpuML.replace("｜","|")
                .replace("（","(")
                .replace("）",")")
                .replace("【","[")
                .replace("】","]");

        ScorePropertyHelper scorePropertyHelper = new ScorePropertyHelper();
        NotationHelper notationHelper = new NotationHelper();

        Map<String, String> metaData = getMetaData(jianpuML);
        for(Map.Entry<String,String> entry: metaData.entrySet()){
            scorePropertyHelper.refreshProperty(entry.getKey(), entry.getValue());
        }

        boolean isStaff = false;
        if(scorePropertyHelper.getAttributeProperty().getStaff()!=null
                && scorePropertyHelper.getAttributeProperty().getStaff().equalsIgnoreCase("true")){
            isStaff = true;//is staff
        }

        ScorePartwise scorePartwise = new ScorePartwise();
        scorePartwise.setWork(createWork(scorePropertyHelper.getWorkProperty()));
        scorePartwise.setIdentification(createIdentification(scorePropertyHelper.getIdentificationProperty()));

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

        String[] keySplit = scorePropertyHelper.getAttributeProperty().getKey().split("\\s+");
        ScorePartwise.Part.Measure lastMeasure = null;

        String[] lines = jianpuML.split("\n");
        Note lastNote = null;
        int scoreLineNo = 0;
        for (String line : lines) {
            if (Strings.isBlank(line)) {
                continue;
            }
            if (line.contains(":")) {
                String[] split = line.split(":");
                scorePropertyHelper.refreshProperty(split[0],split[1]);
                continue;
            }

            scoreLineNo++;
            int scoreLineMeasureNo = 0;

            String[] measureStrs = line.split("[\\|]");
            for (String measureStr : measureStrs) {
                if(Strings.isBlank(measureStr)){
                    continue;
                }
                scoreLineMeasureNo++;
                String origMeasureStr = measureStr;

                measureStr = measureStr.replace("(", " ( ")
                        .replace(")", " ) ")
                        .replace("[", " [ ")
                        .replace("]", " ] ");

                String[] measureSplit = measureStr.split("&");
                ScorePartwise.Part.Measure measure = new ScorePartwise.Part.Measure();
                measure.setNumber(measureNo + "");

                if(scorePropertyHelper.isUpdatingAttribute()){
                    Attributes attributes = createAttributes(scorePropertyHelper.getAttributeProperty());
                    measure.getNoteOrBackupOrForward().add(attributes);
                    scorePropertyHelper.updatedAttribute();
                }
                if(scorePropertyHelper.isUpdatingDirection()){
                    Direction direction = createDirection(scorePropertyHelper.getDirectionProperty());
                    measure.getNoteOrBackupOrForward().add(direction);
                    scorePropertyHelper.updatedDirection();
                }

                lastMeasure = measure;

                if(measureSplit.length>0) {
                    String[] notes = measureSplit[0].trim().split("\\s+"); // only support one line currently
                    for (String noteString : notes) {
                        boolean isNotation = notationHelper.refreshNotation(noteString);
                        if(isNotation){
                            if(notationHelper.isSlurStop()){
                                if(lastNote!=null) {
                                    Notations notations = new Notations();
                                    Slur slur = new Slur();
                                    slur.setType(StartStopContinue.STOP);
                                    notations.getTiedOrSlurOrTuplet().add(slur);
                                    lastNote.getNotations().add(notations);
                                }

                                notationHelper.doneSlurStop();
                            }

                            if(notationHelper.isTupletStop()){
                                if(lastNote!=null) {
                                    Notations notations = new Notations();
                                    Tuplet tuplet = new Tuplet();
                                    tuplet.setType(StartStop.STOP);
                                    notations.getTiedOrSlurOrTuplet().add(tuplet);
                                    lastNote.getNotations().add(notations);

                                    lastNote.setTimeModification(notationHelper.genTimeModification());
                                }

                                notationHelper.doneTupletStop();
                            }

                            continue;
                        }

                        List<Note> staffNotes = convertJianpuNote(keySplit[0], noteString, scorePropertyHelper.getControlVariable().getDefaultDuration(), isStaff);
                        for(Note staffNote: staffNotes){
                            if(notationHelper.isSlurStart()){
                                Notations notations = new Notations();
                                Slur slur = new Slur();
                                slur.setType(StartStopContinue.START);
                                notations.getTiedOrSlurOrTuplet().add(slur);
                                staffNote.getNotations().add(notations);

                                notationHelper.doneSlurStart();
                            }

                            if(notationHelper.isTupletStart()){
                                Notations notations = new Notations();
                                Tuplet tuplet = new Tuplet();
                                tuplet.setType(StartStop.START);
                                notations.getTiedOrSlurOrTuplet().add(tuplet);
                                staffNote.getNotations().add(notations);

                                staffNote.setTimeModification(notationHelper.genTimeModification());

                                notationHelper.doneTupletStart();
                            }else if(notationHelper.isTupletContinue()){
                                staffNote.setTimeModification(notationHelper.genTimeModification());
                            }

                            measure.getNoteOrBackupOrForward().add(staffNote);
                            lastNote = staffNote;
                        }
                    }
                }

                part.getMeasure().add(measure);
                System.out.println("finished "+measureNo+ " measure ("+scoreLineNo+","+scoreLineMeasureNo+"): "+ origMeasureStr);
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
}
