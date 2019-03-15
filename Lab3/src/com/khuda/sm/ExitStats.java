package com.khuda.sm;

import java.util.HashMap;
import java.util.Map;

public class ExitStats extends Element {
    private Map<Integer, Double> time = new HashMap<>();
    private Map<Integer, Integer> count = new HashMap<>();

    public ExitStats() {
        settNext(Double.POSITIVE_INFINITY);
    }

    @Override
    public void inAct(Event event) {
        super.inAct(event);
        super.outAct();
        time.put(event.getType(), time.getOrDefault(event.getType(), 0.0) +
                gettCurr() - event.getCreateTime());
        count.put(event.getType(), count.getOrDefault(event.getType(), 0) + 1);
    }

    public double getAverageTime(int type){
        return time.get(type)/count.get(type);
    }
}
