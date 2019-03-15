package com.khuda.sm;

import java.util.*;

public class Process extends Element {
    private int maxQueue, failure;
    private List<Event> queue = new ArrayList<>();
    private double averageQueue;
    private double avgLoad;
    private int numOfUnits;
    private Map<Event, Double> nextTimes = new HashMap<>();
    private Map<Integer, Double> averageTime = new HashMap<>();
    ChangeQueue changeQueue = null;

    public Process(double delay) {
        super(delay);
        numOfUnits = 1;
        maxQueue = Integer.MAX_VALUE;
        averageQueue = 0.0;
    }

    public Process() {
        super();
        numOfUnits = 1;
        maxQueue = Integer.MAX_VALUE;
        averageQueue = 0.0;
    }

    @Override
    public void inAct(Event event) {
        if (nextTimes.size() < numOfUnits) {
            nextTimes.put(event, super.gettCurr() +
                    super.getDelay(averageTime.getOrDefault(event.getType(), getDelayMean())));
        } else {
            if (queue.size() < getMaxQueue()) {
                queue.add(event);
            } else {
                failure++;
            }
        }
    }


    @Override
    public void outAct() {
        super.outAct();
        double nextTime = gettNext();
        Event e = null;

        for (Map.Entry<Event, Double> i : nextTimes.entrySet()) {
            if (e == null && i.getValue() == nextTime) {
                e = i.getKey();
            }
        }
        nextTimes.remove(e);
        if (getNextElement() != null) {
            getNextElement().inAct(e);
        }
        if (!queue.isEmpty()) {
            Event ev = chooseNextFromQueue();
            queue.remove(ev);
            nextTimes.put(ev, super.gettCurr() +
                    super.getDelay(averageTime.getOrDefault(ev.getType(), getDelayMean())));
        }
        if (changeQueue != null) {
            changeQueue.tryChangeQueue();
        }
    }

    @Override
    public double gettNext() {
        if (nextTimes.isEmpty()) {
            return Double.POSITIVE_INFINITY;
        } else {
            return nextTimes.entrySet().stream().min((a, b) -> {
                        if (a.getValue() < b.getValue()) return -1;
                        else if (a.getValue().equals(b.getValue())) return 0;
                        else return 1;
                    }
            ).get().getValue();
        }
    }

    public void addAverageTime(int type, double time){
        averageTime.put(type, time);
    }

    public Event chooseNextFromQueue(){
        return queue.get(0);
    }

    public int getFailure() {
        return failure;
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

    public void setChangeQueue(ChangeQueue changeQueue) {
        this.changeQueue = changeQueue;
    }

    public List<Event> getQueue() {
        return queue;
    }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("failure = " + this.getFailure());
        System.out.println("quantity = " + getQuantity());
        System.out.println("busy units = " + nextTimes.size());
        System.out.println("queue = " + queue.size());
    }

    @Override
    public void doStatistics(double delta) {
        averageQueue += queue.size() * delta;
        avgLoad += delta * nextTimes.size();
    }

    public double getAverageQueue() {
        return averageQueue;
    }
}

