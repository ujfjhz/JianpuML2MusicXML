package org.chinesemusicscore.JianpuML.property;

import java.util.HashSet;
import java.util.Set;

public class ScorePropertyHelper {
    private WorkProperty workProperty = new WorkProperty();

    private IdentificationProperty identificationProperty = new IdentificationProperty();

    private AttributeProperty attributeProperty = new AttributeProperty();

    private DirectionProperty directionProperty = new DirectionProperty();

    private ControlVariable controlVariable = new ControlVariable();

    private Set<String> updating = new HashSet<>();

    public WorkProperty getWorkProperty() {
        return workProperty;
    }

    public IdentificationProperty getIdentificationProperty() {
        return identificationProperty;
    }

    public AttributeProperty getAttributeProperty() {
        return attributeProperty;
    }

    public DirectionProperty getDirectionProperty() {
        return directionProperty;
    }

    public ControlVariable getControlVariable(){
        return controlVariable;
    }

    public void refreshProperty(String key, String value){
        key = key.trim();
        value = value.trim();

        if(WorkProperty.JP_PROP_TITLE.equalsIgnoreCase(key)){
            workProperty.setTitle(value);
            updating.add("WorkProperty");
        }else if(IdentificationProperty.JP_PROP_COMPOSER.equalsIgnoreCase(key)){
            identificationProperty.setComposer(value);
            updating.add("IdentificationProperty");
        }else if(IdentificationProperty.JP_PROP_ARRANGER.equalsIgnoreCase(key)){
            identificationProperty.setArranger(value);
            updating.add("IdentificationProperty");
        }else if(AttributeProperty.JP_PROP_KEY.equalsIgnoreCase(key)){
            attributeProperty.setKey(value);
            updating.add("AttributeProperty");
        }else if(AttributeProperty.JP_PROP_TimeSignature.equalsIgnoreCase(key)){
            attributeProperty.setTimeSignature(value);
            updating.add("AttributeProperty");
        }else if(DirectionProperty.JP_PROP_TEMPO.equalsIgnoreCase(key)){
            directionProperty.setTempo(value);
            updating.add("DirectionProperty");
        }else if(ControlVariable.JP_PROP_DEFAULTDURATION.equalsIgnoreCase(key)){
            controlVariable.setDefaultDuration(value);
        }
    }

    public boolean isUpdatingWork(){
        return updating.contains("WorkProperty");
    }
    public boolean isUpdatingIdentification(){
        return updating.contains("IdentificationProperty");
    }
    public boolean isUpdatingAttribute(){
        return updating.contains("AttributeProperty");
    }
    public boolean isUpdatingDirection(){
        return updating.contains("DirectionProperty");
    }

    public void updatedWork(){
        updating.remove("WorkProperty");
    }

    public void updatedIdentification(){
        updating.remove("IdentificationProperty");
    }

    public void updatedAttribute(){
        updating.remove("AttributeProperty");
    }

    public void updatedDirection(){
        updating.remove("DirectionProperty");
    }

}
