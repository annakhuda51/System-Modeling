package com.khuda.sm;

public class Element {
    private String name;
    private double tNext;
    private double delayMean, delayDev;
    private String distribution;
    private int quantity;
    private double tCurr;
    private Element nextElement;
    private static int nextId = 0;
    private int id;


    public Element() {
        tNext = 0.0;
        delayMean = 1.0;
        distribution = "exp";
        tCurr = tNext;

        nextElement = null;
        id = nextId;
        nextId++;
        name = "element" + id;
    }

    public Element(double delay) {
        name = "anonymous";
        tNext = 0.0;
        delayMean = delay;
        distribution = "exp";
        tCurr = tNext;
        nextElement = null;
        id = nextId;
        nextId++;
        name = "element" + id;
    }

    public Element(String nameOfElement, double delay) {
        name = nameOfElement;
        tNext = 0.0;
        delayMean = delay;
        distribution = "exp";
        tCurr = tNext;
        nextElement = null;
        id = nextId;
        nextId++;
        name = "element" + id;
    }

    public double getDelay(double delay) {
        if ("exp".equalsIgnoreCase(getDistribution())) {
            delay = FunRand.Exp(delay);
        } else {
            if ("gauss".equalsIgnoreCase(getDistribution())) {
                delay = FunRand.Gauss(delay, getDelayDev());
            } else {
                if ("unif".equalsIgnoreCase(getDistribution())) {
                    delay = FunRand.Unif(delay, getDelayDev());
                } else {
                    if ("".equalsIgnoreCase(getDistribution()))
                        delay = getDelayMean();
                    else if ("erlang".equalsIgnoreCase(getDistribution())){
                        delay = FunRand.erlangDistribution(delay, getDelayDev());
                    }
                }
            }
        }
        return delay;
    }


    public double getDelayDev() {
        return delayDev;
    }

    public void setDelayDev(double delayDev) {
        this.delayDev = delayDev;
    }

    public String getDistribution() {
        return distribution;
    }

    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }

    public int getQuantity() {
        return quantity;
    }

    public double gettCurr() {
        return tCurr;
    }

    public void settCurr(double tCurr) {
        this.tCurr = tCurr;
    }

    public Element getNextElement() {
        return nextElement;
    }

    public void setNextElement(Element nextElement) {
        this.nextElement = nextElement;
    }

    public void inAct(Event event) {

    }

    public void outAct() {
        quantity++;
    }

    public double gettNext() {
        return tNext;
    }


    public void settNext(double tNext) {
        this.tNext = tNext;
    }

    public double getDelayMean() {
        return delayMean;
    }

    public void setDelayMean(double delayMean) {
        this.delayMean = delayMean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void printResult() {
        System.out.println(getName() + "  quantity = " + quantity);
    }

    public void printInfo() {
        System.out.println(getName() + " quantity = " + quantity +
                " tNext= " + gettNext());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void doStatistics(double delta) {

    }
}
