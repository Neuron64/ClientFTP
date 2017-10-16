package com.neuron64.ftp.client.ui.base.bus.event;

/**
 * Created by Neuron on 20.09.2017.
 */

public class ButtonEvent {

    private TypeButtonEvent typeButtonEvent;

    private ButtonEvent(TypeButtonEvent typeButtonEvent){
        this.typeButtonEvent = typeButtonEvent;
    }

    public static ButtonEvent buttonEvent(TypeButtonEvent typeButtonEvent){
        return new ButtonEvent(typeButtonEvent);
    }

    public TypeButtonEvent getTypeButtonEvent() {
        return typeButtonEvent;
    }

    public enum TypeButtonEvent{
        FLOATING_BN,
        ON_BACK_PRESSED
    }

}
