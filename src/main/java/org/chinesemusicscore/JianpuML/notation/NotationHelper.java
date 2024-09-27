package org.chinesemusicscore.JianpuML.notation;

import org.audiveris.proxymusic.TimeModification;

import java.math.BigInteger;

public class NotationHelper {
    private boolean slurStart = false;
    private boolean slurStop = false;

    private boolean tupletStart = false;
    private boolean tupletContinue = false;
    private boolean tupletStop = false;

    public boolean refreshNotation(String notation){
        boolean isNotation = true;

        if("(".equalsIgnoreCase(notation)){
            slurStart = true;
        }else if(")".equalsIgnoreCase(notation)){
            slurStop = true;
        }else if("[".equalsIgnoreCase(notation)){
            tupletStart = true;
        }else if("]".equalsIgnoreCase(notation)){
            tupletStop = true;
            tupletContinue = false;
        } else {
            isNotation = false;
        }

        return isNotation;
    }

    public TimeModification genTimeModification(){
        TimeModification timeModification = new TimeModification();
        timeModification.setActualNotes(BigInteger.valueOf(3));
        timeModification.setNormalNotes(BigInteger.valueOf(2));

        return timeModification;
    }

    public boolean isSlurStart() {
        return slurStart;
    }

    public boolean isSlurStop() {
        return slurStop;
    }

    public void doneSlurStart(){
        this.slurStart = false;
    }

    public void doneSlurStop(){
        this.slurStop = false;
    }

    public boolean isTupletStart() {
        return tupletStart;
    }

    public boolean isTupletStop() {
        return tupletStop;
    }

    public boolean isTupletContinue(){
        return tupletContinue;
    }

    public void doneTupletStart(){
        this.tupletStart = false;
        this.tupletContinue = true;
    }

    public void doneTupletStop(){
        this.tupletStop = false;
    }
}
