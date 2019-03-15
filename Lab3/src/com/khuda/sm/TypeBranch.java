package com.khuda.sm;

import java.util.HashMap;
import java.util.Map;

public class TypeBranch extends Element {
    private Map<Integer, Element> nextElements = new HashMap<>();

    @Override
    public void inAct(Event event) {
        nextElements.get(event.getType()).inAct(event);
    }

    public void addNext(int type, Element e){
        nextElements.put(type, e);
    }
}
