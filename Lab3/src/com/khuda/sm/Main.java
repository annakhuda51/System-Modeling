package com.khuda.sm;


import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        task1();
        task2();
    }

    private static void task1(){
        Create c = new Create(2);
        Process p1 = new Process(0.6);
        Process p2 = new Process(0.3);
        Process p3 = new Process(0.4);
        Process p4 = new Process(0.1);
        Branch b = new Branch();
        p4.setNumOfUnits(2);

        c.setName("CREATE");
        p1.setName("PROCESS 1");
        p2.setName("PROCESS 2");
        p3.setName("PROCESS 3");
        p4.setName("PROCESS 4");

        c.setNextElement(p1);
        p1.setNextElement(b);
        b.addNext(p2, 0.15);
        b.addNext(p3, 0.13);
        b.addNext(p4, 0.3);
        p2.setNextElement(p1);
        p3.setNextElement(p1);
        p4.setNextElement(p1);


        Model m = new Model(Arrays.asList(c, p1, p2, p3, p4));
        m.simulate(10000000);
    }

    private  static void task2(){
        Create c = new Create(0.5);
        Process p1 = new Process(0.3);
        Process p2 = new Process(0.3);
        MinQueueBranch b = new MinQueueBranch();
        ChangeQueue changeQueue = new ChangeQueue(p1, p2);

        c.setName("CREATE");
        p1.setName("SELLER 1");
        p2.setName("SELLER 2");
        p1.setMaxQueue(3);
        p2.setMaxQueue(3);

        c.setNextElement(b);
        b.addNext(p1);
        b.addNext(p2);

        Model m = new Model(Arrays.asList(c, p1, p2));
        m.simulate(10000);

        System.out.println("Number of changes of queue: " + changeQueue.getNumOfChanges());
    }

}
