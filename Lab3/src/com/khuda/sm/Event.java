package com.khuda.sm;

public class Event extends Element {
    private int type;
    private double createTime;

    public Event(int type, double createTime) {
        this.type = type;
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public double getCreateTime() {
        return createTime;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCreateTime(double createTime) {
        this.createTime = createTime;
    }

}
