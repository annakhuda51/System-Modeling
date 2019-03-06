package com.khuda.sysmodeling;

import java.util.ArrayList;
import java.util.List;

public class Process extends Element {

    private int queue, maxQueue, failure;
    private double averageQueue;
    private double avgLoad;
    private int numOfUnits;
    private List<Double> nextTimes = new ArrayList<>();

    public Process(double delay) {
        super(delay);
        queue = 0;
        numOfUnits = 1;
        maxQueue = Integer.MAX_VALUE;
        averageQueue = 0.0;
    }

    @Override
    public void inAct() {
        if (nextTimes.size() < numOfUnits) {
            nextTimes.add(super.gettCurr() + super.getDelay());
        } else {
            if (getQueue() < getMaxQueue()) {
                setQueue(getQueue() + 1);
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct() {
        super.outAct();
        double nextTime = gettNext();
        nextTimes.remove(nextTime);

        if (getQueue() > 0) {
            setQueue(getQueue() - 1);
            nextTimes.add(super.gettCurr() + super.getDelay());
        }
        if (getNextElement() != null) {
            getNextElement().inAct();
        }
    }

    @Override
    public double gettNext() {
        if (nextTimes.isEmpty()) {
            return Double.POSITIVE_INFINITY;
        } else {
            return nextTimes.stream().min((a, b) -> {
                        if (a < b) return -1;
                        else if (a == b) return 0;
                        else return 1;
                    }
            ).get();
        }
    }

    public int getFailure() {
        return failure;
    }

    public int getQueue() {
        return queue;
    }


    public void setQueue(int queue) {
        this.queue = queue;
    }


    public int getMaxQueue() {
        return maxQueue;
    }


    public void setMaxQueue(int maxQueue) {
        this.maxQueue = maxQueue;
    }

    public double getAvgLoad() {
        return avgLoad / gettCurr();
    }

    public int getNumOfUnits() {
        return numOfUnits;
    }

    public void setNumOfUnits(int numOfUnits) {
        this.numOfUnits = numOfUnits;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure = " + this.getFailure());
        System.out.println("quantity = " + getQuantity());
        System.out.println("busy units = " + nextTimes.size());
    }

    @Override
    public void doStatistics(double delta) {
        averageQueue = getAverageQueue() + queue * delta;
        avgLoad += delta * nextTimes.size();

    }

    public double getAverageQueue() {
        return averageQueue;
    }
}

