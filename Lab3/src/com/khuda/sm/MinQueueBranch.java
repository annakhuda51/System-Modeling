package com.khuda.sm;

import java.util.ArrayList;
import java.util.List;

public class MinQueueBranch extends Element {
    private List<Process> nextElements= new ArrayList<> ();

    public void addNext(Process p){
        nextElements.add(p);
    }

    @Override
    public void inAct(Event e){
        super.inAct(e);
        Process p = choose();
        p.inAct(e);
    }

    private Process choose() {
        Process min = nextElements.get(0);
        for(int i = 0; i<nextElements.size(); i++){
            if(nextElements.get(i).getQueue().size() < min.getQueue().size()){
                min = nextElements.get(i);
            }
        }
        return min;
    }
}
