package com.khuda.sysmodeling;

import java.util.ArrayList;

public class Model {

    private ArrayList<Element> list = new ArrayList<>();
    double tNext, tCurr;
    int event;

    public Model(ArrayList<Element> elements) {
        list = elements;
        tNext = 0.0;
        event = 0;
        tCurr = tNext;
        tNext = Double.MAX_VALUE;
    }


    public void simulate(double time) {

        while (tCurr < time) {
            tNext = Double.MAX_VALUE;
            for (Element e : list) {
                if (e.gettNext() < tNext) {
                    tNext = e.gettNext();
                    event = e.getId();

                }
            }
            System.out.println("\nIt's time for event in " +
                    list.get(event).getName() +
                    ", time =   " + tNext);
            for (Element e : list) {
                e.doStatistics(tNext - tCurr);
            }
            tCurr = tNext;
            for (Element e : list) {
                e.settCurr(tCurr);
            }
            list.get(event).outAct();
            for (Element e : list) {
                if (e.gettNext() == tCurr) {
                    e.outAct();
                }
            }
            printInfo();
        }
        printResult();
    }

    public void printInfo() {
        for (Element e : list) {
            e.printInfo();
        }
    }

    public void printResult() {
        System.out.println("\n-------------RESULTS-------------");
        for (Element e : list) {
            e.printResult();
            if (e instanceof Process) {
                Process p = (Process) e;
                System.out.println("average length of queue = " +
                        p.getAverageQueue() / tCurr
                        + "\nfailure probability  = " +
                        p.getFailure() / (double) (p.getQuantity() + p.getFailure()) +
                        "\naverage load = " + p.getAvgLoad());
            }
        }
    }
}
