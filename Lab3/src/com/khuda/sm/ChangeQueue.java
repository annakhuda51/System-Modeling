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
        if (p1.getQueue() - p2.getQueue() >= 2) {
            p1.setQueue(p1.getQueue() - 1);
            p2.setQueue(p2.getQueue() + 1);
            numOfChanges++;
        } else if (p2.getQueue() - p1.getQueue() >= 2) {
            p2.setQueue(p2.getQueue() - 1);
            p1.setQueue(p1.getQueue() + 1);
            numOfChanges++;
        }
    }

    public int getNumOfChanges() {
        return numOfChanges;
    }
}
