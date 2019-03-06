package com.khuda.sysmodeling;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Create c = new Create(2.0);
        Process p1 = new Process(5.0);
        Process p2 = new Process(5.0);
        Process p3 = new Process(2.0);
        c.setNextElement(p1);
        p1.setNextElement(p2);
        p2.setNextElement(p3);
        p1.setMaxQueue(6);
        p2.setMaxQueue(6);
        p3.setMaxQueue(6);
        c.setName("CREATOR");
        p1.setName("PROCESS 1");
        p2.setName("PROCESS 2");
        p3.setName("PROCESS 3");
        c.setDistribution("exp");
        p1.setDistribution("exp");
        p2.setDistribution("exp");
        p3.setDistribution("exp");

        p1.setNumOfUnits(1);
        p2.setNumOfUnits(1);
        p3.setNumOfUnits(1);

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        Model model = new Model(list);
        model.simulate(1000.0);
    }

}


