package com.khuda.sm;

import java.util.HashMap;
import java.util.Map;

public class Branch extends Element {
    private Map<Element, Double> elements = new HashMap<>();

    @Override
    public void inAct() {
        super.inAct();
        Element el = choose();
        if(el != null){
            el.inAct();
        }
    }

    public void addNext(Element el, double prob) {
        elements.put(el, prob);
    }

    private Element choose() {
        double r = Math.random();
        double s = 0;
        for (Map.Entry<Element, Double> kv : elements.entrySet()) {
            s += kv.getValue();
            if (s > r) {
                return kv.getKey();
            }
        }
        return null;
    }
}
