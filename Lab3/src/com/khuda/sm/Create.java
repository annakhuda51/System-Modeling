package com.khuda.sm;

import java.util.HashMap;
import java.util.Map;

public class Create extends Element {
    public Create(double delay) {
        super(delay);
    }

    private Map<Integer, Double> probability = new HashMap<>(); //probability of some type created

    public void addType(int type, double p) {
        probability.put(type, p);
    }

    @Override
    public void outAct() {
        super.outAct();
        super.settNext(super.gettCurr() + super.getDelay(getDelayMean()));
        super.getNextElement().inAct(new Event(choose(), gettCurr()));
    }

    private int choose() {
        double r = Math.random();
        double s = 0;
        for (Map.Entry<Integer, Double> kv : probability.entrySet()) {
            s += kv.getValue();
            if (s > r) {
                return kv.getKey();
            }
        }
        return 1;
    }
}