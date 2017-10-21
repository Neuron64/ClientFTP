package com.neuron64.ftp.client.ui.base.bus.event;

/**
 * Created by Neuron on 20.09.2017.
 */

public class ButtonEvent {

    private TypeButtonEvent typeButtonEvent;

    private String nameClass;

    private ButtonEvent(TypeButtonEvent typeButtonEvent, String nameClass){
        this.typeButtonEvent = typeButtonEvent;
        this.nameClass = nameClass;
    }

    public static ButtonEvent buttonEvent(TypeButtonEvent typeButtonEvent){
        return new ButtonEvent(typeButtonEvent, null);
    }

    public static ButtonEvent buttonEvent(TypeButtonEvent typeButtonEvent, String nameClass){
        return new ButtonEvent(typeButtonEvent, nameClass);
    }

    public TypeButtonEvent getTypeButtonEvent() {
        return typeButtonEvent;
    }

    public String getNameClass() {
        return nameClass;
    }

    public enum TypeButtonEvent{
        FLOATING_BN,
        ON_BACK_PRESSED
    }

}
