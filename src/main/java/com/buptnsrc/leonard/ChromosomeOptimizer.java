package com.buptnsrc.leonard;

import java.util.Date;
import java.util.Random;

/**
 * Created by leonard on 16-7-25.
 */
public class ChromosomeOptimizer {

    private final int MAX_EVOLVE_TIME = 30;
    private final int CHROMOSOME_NUM = 30;
    private final double CROSS_PROB = 0.6;
    private final int MACHINE_NUM = Constants.MACHINE_NUM;
    private final int TASK_NUM = Constants.TASK_NUM;
    public static Random random = new Random(new Date().getTime());
    private int[][] myChromosome;
    private int maxTime;
    Matrix matrix;

    public ChromosomeOptimizer(int[] chromosome, Matrix matrix){

        this.matrix = matrix;

        myChromosome = new int[MACHINE_NUM][TASK_NUM];
        for(int i=0;i<MACHINE_NUM;i++){
            for(int j=0;j<TASK_NUM;j++){
                myChromosome[i][j] = 0;
            }
        }
        for(int i=0;i<TASK_NUM;i++){
            myChromosome[chromosome[i]][i] = 1;
        }

        maxTime = getTime(myChromosome);
    }

    private void cross(){

        int[][] newChromosome = new int[MACHINE_NUM][TASK_NUM];
        for(int i=0;i<MACHINE_NUM;i++){
            System.arraycopy(myChromosome[i],0,newChromosome[i],0,TASK_NUM);
        }
    }

    private int getTime(int[][] newChromosome){

        int max = 0;
        for(int i=0;i<MACHINE_NUM;i++){
            int sum = 0;
            for(int j=0; j<TASK_NUM;j++){
                if(newChromosome[i][j] == 1){
                    sum += matrix.matrix[i][j];
                }
            }
            if(sum > max){
                max = sum;
            }
        }
        return max;
    }



    public int[][] getChromosomes(){

        return new int[CHROMOSOME_NUM][TASK_NUM];
    }

    public static void main(String[] args){
    }
}
