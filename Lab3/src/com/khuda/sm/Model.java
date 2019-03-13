package com.khuda.sm;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private List<Element> list = new ArrayList<>();
    double tNext, tCurr;
    Element curr;

    public Model(List<Element> elements) {
        list = elements;
        tNext = 0.0;
        tCurr = tNext;
        tNext = Double.MAX_VALUE;
    }


    public void simulate(double time) {

        while (tCurr < time) {
            tNext = Double.POSITIVE_INFINITY;
            for (Element e : list) {
                if (e.gettNext() < tNext) {
                    tNext = e.gettNext();
                    curr = e;

                }
            }
//            System.out.println("\nIt's time for event in " +
//                    curr.getName() +
//                    ", time =   " + tNext);
            for (Element e : list) {
                e.doStatistics(tNext - tCurr);
            }
            tCurr = tNext;
            for (Element e : list) {
                e.settCurr(tCurr);
            }
            for (Element e : list) {
                if (e.gettNext() == tCurr) {
                    e.outAct();
                }
            }
           // printInfo();
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
                        (p.getAverageQueue() /  p.gettCurr())
                        + "\nfailure probability  = " +
                        p.getFailure() / (double) (p.getQuantity() + p.getFailure()) +
                        "\naverage load = " + p.getAvgLoad());
            }
        }
    }
}
