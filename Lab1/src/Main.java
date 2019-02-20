import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.swing.*;
import java.awt.*;

import static java.lang.Math.*;

public class Main {
    public static int SIZE = 10000;
    public static void main(String[] args){
        showExpDistribution(0.3);
        showGaussDistribution(0.3, 2);
        showUniformDistribution(50);
    }

    public static double[] expDistribution(double lambda){
        double[] arr = new double[SIZE];
        for (int i=0; i<SIZE; i++){
            arr[i] = -(1/lambda) * log(random());
        }
        return arr;
    }

    public static double[] gaussDistribution(double sigma, double a){
        double[] arr = new double[SIZE];
        for(int i=0; i<SIZE; i++){
            double m = 0;
            for(int j = 1; j<=12; j++){
                m += random();
            }
            m -=6;
            arr[i] = sigma*m + a;
        }
        return arr;
    }

    public static double[] uniformDistribution(long z){
        long a = (long)pow(5, 13);
        long c = (long)pow(2, 31);
        double[] arr = new double[SIZE];
        for(int i =0; i<SIZE; i++){
            arr[i] = (double)z/c;
            z = a*z%c;
        }
        return arr;
    }

    public static double avg(double[] arr){
        double avg = 0;
        for(int i = 0; i<SIZE; i++){
            avg+=arr[i];
        }
        return avg/SIZE;
    }

    public static double variance(double[] arr){
        double v = 0;
        double avg = avg(arr);
        for(int i=0; i<SIZE; i++){
            v += pow((arr[i] - avg), 2)/SIZE;
        }
        return v;
    }

    public static boolean checkExpDistribution(double[] arr, double lambda){
        double min = Double.MAX_VALUE ;
        double max = Double.MIN_VALUE;
        double sum = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i]<min){
                min = arr[i];
            }
            if(arr[i]>max){
                max = arr[i];
            }
        }
        double interval = (max-min)/20;
        for(int i=0; i<20; i++){
          int count = 0;
          double start = i*interval;
          double end = (i+1)*interval;
          double probability = (1- exp(-lambda*end))-(1-exp(-lambda*start));
          for(int j=0; j<arr.length; j++){
              if(arr[j]>= start && arr[j]<end){
                  count++;
              }
          }
          sum += pow((count-SIZE*probability), 2)/ (SIZE*probability);
        }
        return sum<28.9;
    }

    public static boolean checkGaussDistribution(double[] arr, double sigma, double a){
        double min = Double.MAX_VALUE ;
        double max = Double.MIN_VALUE;
        double sum = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i]<min){
                min = arr[i];
            }
            if(arr[i]>max){
                max = arr[i];
            }
        }
        double interval = (max-min)/20;
        for(int i=0; i<20; i++){
            int count = 0;
            double start = i*interval;
            double end = (i+1)*interval;
            double probability = interval/(sigma*sqrt(2*PI))*exp(-pow(((start+end)/2)-a, 2)/(2*pow(sigma, 2)));
            for(int j=0; j<arr.length; j++){
                if(arr[j]>= start && arr[j]<end){
                    count++;
                }
            }
            sum += pow((count-SIZE*probability), 2)/ (SIZE*probability);
        }
        return sum<27.6;
    }

    public static boolean checkUniformDistribution(double[] arr){
        double min = Double.MAX_VALUE ;
        double max = Double.MIN_VALUE;
        double sum = 0;
        for(int i=0; i<arr.length; i++){
            if(arr[i]<min){
                min = arr[i];
            }
            if(arr[i]>max){
                max = arr[i];
            }
        }
        double interval = (max-min)/20;
        for(int i=0; i<20; i++){
            int count = 0;
            double start = i*interval;
            double end = (i+1)*interval;
            double probability = interval;
            for(int j=0; j<arr.length; j++){
                if(arr[j]>= start && arr[j]<end){
                    count++;
                }
            }
            sum += pow((count-SIZE*probability), 2)/ (SIZE*probability);
        }
        return sum<28.9;
    }

    public static void showExpDistribution( double lambda){
        double[] arrExp = expDistribution(lambda);
        drawHistogram(arrExp, "Exponential " + lambda);
        System.out.println("Exponential Distribution: \nAverage: " + avg(arrExp) + "\nVariance: " + variance(arrExp));
        if (checkExpDistribution(arrExp, lambda) == true){
            System.out.println("Pearson's test passed");
        } else
            System.out.println("Pearson's test failed");
    }

    public static void showGaussDistribution( double sigma, double a){
        double[] arrGauss = gaussDistribution(sigma, a);
        drawHistogram(arrGauss, "Gauss " + sigma + ", " + a);
        System.out.println("\nGauss Distribution: \nAverage: " + avg(arrGauss) + "\nVariance: " + variance(arrGauss));
        if (checkGaussDistribution(arrGauss, sigma, a) == true){
            System.out.println("Pearson's test passed");
        } else
            System.out.println("Pearson's test failed");
    }

    public static void showUniformDistribution(long z){
        double[] arrUniform = uniformDistribution(z);
        drawHistogram(arrUniform, "Uniform " + z);
        System.out.println("\nUniform Distribution: \nAverage: " + avg(arrUniform) + "\nVariance: " + variance(arrUniform));
        if (checkUniformDistribution(arrUniform) == true){
            System.out.println("Pearson's test passed");
        } else
            System.out.println("Pearson's test failed");
    }


    public static void drawHistogram(double[] arr, String name){
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        dataset.addSeries("Hist",arr,20);
        PlotOrientation orientation = PlotOrientation.VERTICAL;

        JFreeChart chart = ChartFactory.createHistogram("", "", "",
                dataset, orientation, false, false, false);
        chart.setBackgroundPaint(Color.white);
        JFrame frame = new JFrame(name);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel aPanel = new JPanel();
        aPanel.setPreferredSize(new Dimension(600, 300));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 300));
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }


}
