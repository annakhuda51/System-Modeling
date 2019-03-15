package com.khuda.sm;


import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
//        task1();
        //task2();
        task3();
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

    private static void task3(){
        TypeBranch tp1 = new TypeBranch();
        TypeBranch tp2 = new TypeBranch();
        ExitStats exit = new ExitStats();
        ChangeType ct = new ChangeType(1);
        exit.setName("EXIT");

        Create c = new Create(15);
        c.setName("COMING TO RECEPTION");
        c.addType(1, 0.5);
        c.addType(2, 0.1);
        c.addType(3, 0.4);

        Process reception = new Process(90){
            @Override
            public Event chooseNextFromQueue() {
                for(Event e: getQueue()){
                    if(e.getType() == 1){
                        return e;
                    }


                }
                return super.chooseNextFromQueue();
            }
        };
        reception.setName("RECEPTION");
        reception.addAverageTime(1, 15);
        reception.addAverageTime(2, 40);
        reception.addAverageTime(3, 30);
        reception.setNumOfUnits(2);

        Process convoy = new Process(3);
        convoy.setDelayDev(8);
        convoy.setDistribution("unif");
        convoy.setName("COMING TO WARD");
        convoy.setNumOfUnits(3);

        Process hall1 = new Process(2);
        hall1.setDelayDev(5);
        hall1.setDistribution("unif");
        hall1.setName("COMING TO REGISTRATION");
        hall1.setNumOfUnits(Integer.MAX_VALUE);

        Process registration = new Process(4.5);
        registration.setDelayDev(3);
        registration.setDistribution("erlang");
        registration.setName("REGISTRATION");

        Process laboratory = new Process(4);
        laboratory.setDelayDev(2);
        laboratory.setDistribution("erlang");
        laboratory.setName("LABORATORY");
        laboratory.setNumOfUnits(2);

        Process hall2 = new Process(2);
        hall2.setDelayDev(5);
        hall2.setDistribution("unif");
        hall2.setName("COMING TO RECEPTION");
        hall2.setNumOfUnits(Integer.MAX_VALUE);

        c.setNextElement(reception);
        reception.setNextElement(tp1);
        tp1.addNext(1, convoy);
        tp1.addNext(2, hall1);
        tp1.addNext(3, hall1);
        convoy.setNextElement(exit);
        hall1.setNextElement(registration);
        registration.setNextElement(laboratory);
        laboratory.setNextElement(tp2);
        tp2.addNext(2, hall2);
        tp2.addNext(3, exit);
        hall2.setNextElement(ct);
        ct.setNextElement(reception);

        Model m = new Model(Arrays.asList(c, exit, reception, convoy, hall1, hall2, registration, laboratory));
        m.simulate(100000);

        System.out.println("Type 1 avg time in system: " + exit.getAverageTime(1));
        System.out.println("Type 3 avg time in system: " + exit.getAverageTime(3));

    }
}
