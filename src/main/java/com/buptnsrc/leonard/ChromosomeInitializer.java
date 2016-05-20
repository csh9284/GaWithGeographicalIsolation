package com.buptnsrc.leonard;

/**
 * Created by leonard on 16-5-20.
 */
public class ChromosomeInitializer {

    private static final int CHROMOSOME_NUM = 30;

    private static final int TASK_NUM = Constants.TASK_NUM;

    private static final int MACHINE_NUM = Constants.MACHINE_NUM;

    private static boolean isInitialed = false;

    private static int[][] chromosomes;

    private static void initChromosome(){

        chromosomes = new int[CHROMOSOME_NUM][TASK_NUM];
        for(int i=0;i<CHROMOSOME_NUM;i++){
            for(int j=0;j<TASK_NUM;j++) {
                chromosomes[i][j] = (int)(Math.random()*MACHINE_NUM);
            }
        }
    }

    public static int[][] getChromosomes(){
        if(!isInitialed){
            initChromosome();
            isInitialed = true;
        }
        int[][] chromosomes01 = new int[CHROMOSOME_NUM][TASK_NUM];
        for(int i=0;i<CHROMOSOME_NUM;i++){
            System.arraycopy(chromosomes[i],0,chromosomes01[i],0,TASK_NUM);
        }
        return chromosomes01;
    }
}
