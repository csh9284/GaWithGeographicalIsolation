package com.buptnsrc.leonard;

import com.buptnsrc.leonard.charts.*;
import org.jfree.ui.RefineryUtilities;

/**
 * Created by leonard on 2015/6/11.
 */
public class Run {

    public static void main(String[] args){

        Matrix matrix = new Matrix();
        matrix.matrix = new int[Constants.MACHINE_NUM][Constants.TASK_NUM];
        for(int i=0;i<Constants.TASK_NUM;i++){
            matrix.matrix[0][i] = (int) (Math.random()*100);
        }
        for(int i=1;i<Constants.MACHINE_NUM;i++){
            for(int j=0;j<Constants.TASK_NUM;j++){
                matrix.matrix[i][j] = matrix.matrix[i-1][j] + (int)(matrix.matrix[i-1][j] * Math.random()*0.2 + 1);
            }
        }
        for(int i=0;i<Constants.MACHINE_NUM;i++){
            for(int j=0;j<Constants.TASK_NUM;j++){
                System.out.print(matrix.matrix[i][j] + " ");
            }
            System.out.println();
        }

        ClassicGa ga = new ClassicGa(matrix);
        int[] g = ga.process();
        GaWithGeographicalIsolation ga2 = new GaWithGeographicalIsolation(matrix);
        int[] g2 = ga2.process();

        LineChartMaker02 lineChart = new LineChartMaker02("GA",g,g2);
        lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        lineChart.setVisible(true);

        System.out.println("classic ga:");
        for(int i=0;i<1000;i++){
            System.out.println(g[i]);
        }
        System.out.println("geo ga:");
        for(int i=0;i<1000;i++){
            System.out.println(g2[i]);
        }

        MinMin min = new MinMin(matrix);
        MaxMin2 maxMin2 = new MaxMin2(matrix);

        int[] datas = new int[4];
        datas[0] = min.process();
        datas[1] = maxMin2.process();
        datas[2] = g[g.length-1];
        datas[3] = g[g2.length-1];
        BarChartMaker localBarChart3DDemo2 = new BarChartMaker("Ga With Geo",datas);
        localBarChart3DDemo2.pack();
        RefineryUtilities.centerFrameOnScreen(localBarChart3DDemo2);
        localBarChart3DDemo2.setVisible(true);
    }
}

/*
save matrix

        com.buptnsrc.leonard.Matrix matrix = new com.buptnsrc.leonard.Matrix();
        matrix.matrix = new int[5][20];
        matrix.matrix[0][0] = 13;
        matrix.matrix[0][1] = 15;
        matrix.matrix[0][2] = 6;
        matrix.matrix[0][3] = 20;
        matrix.matrix[0][4] = 32;
        matrix.matrix[0][5] = 16;
        matrix.matrix[0][6] = 23;
        matrix.matrix[0][7] = 22;
        matrix.matrix[0][8] = 50;
        matrix.matrix[0][9] = 30;
        matrix.matrix[0][10] = 7;
        matrix.matrix[0][11] = 11;
        matrix.matrix[0][12] = 15;
        matrix.matrix[0][13] = 14;
        matrix.matrix[0][14] = 23;
        matrix.matrix[0][15] = 27;
        matrix.matrix[0][16] = 31;
        matrix.matrix[0][17] = 54;
        matrix.matrix[0][18] = 63;
        matrix.matrix[0][19] = 40;

        matrix.matrix[i][j] = matrix.matrix[i-1][j] + (int)(Math.random()*3 + 1);
*/

