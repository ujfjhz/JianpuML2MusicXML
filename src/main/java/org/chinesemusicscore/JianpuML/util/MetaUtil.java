package org.chinesemusicscore.JianpuML.util;

import org.apache.logging.log4j.util.Strings;
import org.audiveris.proxymusic.*;
import org.chinesemusicscore.JianpuML.property.AttributeProperty;
import org.chinesemusicscore.JianpuML.property.DirectionProperty;
import org.chinesemusicscore.JianpuML.property.IdentificationProperty;
import org.chinesemusicscore.JianpuML.property.WorkProperty;

import javax.xml.bind.JAXBElement;
import java.lang.String;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class MetaUtil {
    public static Map<String,String> getMetaData(String jianpuML){
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

    public static Work createWork(WorkProperty workProperty){
        Work work = new Work();
        if(workProperty.getTitle().isBlank()){
            work.setWorkTitle("Title");
        }else {
            work.setWorkTitle(workProperty.getTitle());
        }
        return work;
    }

    public static Identification createIdentification(IdentificationProperty identificationProperty){
        Identification identification = new Identification();

        TypedText arranger = new TypedText();
        arranger.setType("arranger");
        String arrangerText;
        if(Strings.isEmpty(identificationProperty.getArranger())){
            arrangerText = "ChineseMusicScore";
        }else {
            arrangerText = identificationProperty.getArranger();
        }
        arranger.setValue(arrangerText);
        identification.getCreator().add(arranger);

        TypedText composer = new TypedText();
        composer.setType("composer");
        String composerText;
        if(Strings.isEmpty(identificationProperty.getComposer())){
            composerText = "Composer";
        }else {
            composerText = identificationProperty.getComposer();
        }
        composer.setValue("Composer: "+composerText+"\nArranger: "+arrangerText);

        identification.getCreator().add(composer);

        TypedText right = new TypedText();
        right.setValue("by ChineseMusicScore (CC BY-NC-SA 4.0)");
        identification.getRights().add(right);

        return identification;
    }

    public static Attributes createAttributes(AttributeProperty attributeProperty){
        Attributes attributes = new Attributes();
        ObjectFactory factory = new ObjectFactory();
        attributes.setDivisions(new BigDecimal("4"));

        Key key = new Key();
        String[] keySplit = attributeProperty.getKey().split("\\s+");

        if("C".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(0));
        }else if("G".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(1));
        }else if("D".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(2));
        }else if("A".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(3));
        }else if("E".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(4));
        }else if("B".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(5));
        }else if("F#".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(6));
        }else if("C#".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(7));
        }else if("F".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(-1));
        }else if("Bb".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(-2));
        }else if("Eb".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(-3));
        }else if("Ab".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(-4));
        }else if("Db".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(-5));
        }else if("Gb".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(-6));
        }else if("Cb".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(-7));
        }

        attributes.getKey().add(key);

        Time time = new Time();
        String timeSignature = attributeProperty.getTimeSignature();
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

    public static Direction createDirection(DirectionProperty directionProperty){
        String tempo = directionProperty.getTempo();
        if(tempo.isBlank()){
            return null;
        }

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
}
