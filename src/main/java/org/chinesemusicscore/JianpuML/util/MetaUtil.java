package org.chinesemusicscore.JianpuML.util;

import org.audiveris.proxymusic.*;

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

    public static Work createWork(Map<String,String> metaData){
        Work work = new Work();
        work.setWorkTitle(metaData.getOrDefault("Title", "Title"));
        return work;
    }

    public static Identification createIdentification(Map<String,String> metaData){
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
        right.setValue("by ChineseMusicScore (CC BY-NC-SA 4.0)");
        identification.getRights().add(right);

        return identification;
    }

    public static Attributes createAttributes(Map<String,String> metaData){
        Attributes attributes = new Attributes();
        ObjectFactory factory = new ObjectFactory();
        attributes.setDivisions(new BigDecimal("4"));

        Key key = new Key();
        String[] keySplit = metaData.get("Key").split("\\s+");
        if("D".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(2));
        }else if("G".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(1));
        }else if("C".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(0));
        }else if("F".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(-1));
        }else if("Bb".equalsIgnoreCase(keySplit[0])){
            key.setFifths(BigInteger.valueOf(-2));
        }
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

    public static Direction createDirection(Map<String,String> metaData){
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
}
