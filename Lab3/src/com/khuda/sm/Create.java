package com.khuda.sm;

public class Create extends Element {
    public Create(double delay) {
        super(delay);
    }

    @Override
    public void outAct() {
        super.outAct();
        super.settNext(super.gettCurr() + super.getDelay());
        super.getNextElement().inAct();
    }
}