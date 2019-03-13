package com.khuda.sm;

import java.util.Random;

public class FunRand {
    public static double Exp(double timeMean) {
        double a = 0;
        while (a == 0) {
            a = Math.random();
        }
        a = -timeMean * Math.log(a);
        return a;
    }

    public static double Unif(double timeMin, double timeMax) {
        double a = 0;
        while (a == 0) {
            a = Math.random();
        }
        a = timeMin + a * (timeMax - timeMin);
        return a;
    }

    public static double Gauss(double timeMean, double
            timeDeviation) {
        double a;
        Random r = new Random();
        a = timeMean + timeDeviation * r.nextGaussian();
        return a;
    }

    public static double erlangDistribution(double timeMean, double k){
        double lyambda = k/timeMean;
        double a = 1;
        for(int i = 0; i<k; i++){
            a *= Math.random();
        }
         return Math.log(a)*(-1/lyambda);
    }
}