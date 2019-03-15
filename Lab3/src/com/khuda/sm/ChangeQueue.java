package com.khuda.sm;

public class ChangeQueue {
    Process p1;
    Process p2;
    int numOfChanges = 0;

    public ChangeQueue(Process p1, Process p2) {
        this.p1 = p1;
        this.p2 = p2;
        p1.setChangeQueue(this);
        p2.setChangeQueue(this);
    }

    public void tryChangeQueue() {
        if (p1.getQueue().size() - p2.getQueue().size() >= 2) {
            Event e = p1.getQueue().get(p1.getQueue().size() - 1);
            p1.getQueue().remove(p1.getQueue().size() - 1);
            p2.getQueue().add(e);
            numOfChanges++;
        } else if (p2.getQueue().size() - p1.getQueue().size() >= 2) {
            Event e = p2.getQueue().get(p2.getQueue().size() - 1);
            p2.getQueue().remove(p2.getQueue().size() - 1);
            p1.getQueue().add(e);
            numOfChanges++;
        }
    }

    public int getNumOfChanges() {
        return numOfChanges;
    }
}
