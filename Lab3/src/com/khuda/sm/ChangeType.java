package com.khuda.sm;

public class ChangeType extends Element{
    int newType;

    public ChangeType(int newType) {
        this.newType = newType;
    }

    @Override
    public void inAct(Event event) {
        event.setType(newType);
        getNextElement().inAct(event);
    }
}
