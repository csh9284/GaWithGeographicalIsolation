package com.buptnsrc.leonard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by leonard on 16-3-29.
 */
public class GaWithGeographicalIsolation {

    private final int MAX_EVOLVE_TIME = 1000;
    private final int CHROMOSOME_NUM = 30;
    private final double CROSS_PROB = 0.6;
    private final double MUTATE_PROB = 0.005;
    private final int MACHINE_NUM = Constants.MACHINE_NUM;
    private final int TASK_NUM = Constants.TASK_NUM;
    public static Random random = new Random(new Date().getTime());

    int[][] chromosomes;
    int[][] chromosomes_01;
    int[][] chromosomes_02;
    int[][] chromosomes_03;
    int[] oldElite_01 = new int[TASK_NUM];
    int[] oldElite_02 = new int[TASK_NUM];
    int[] oldElite_03 = new int[TASK_NUM];
    int oldEliteTime_01;
    int oldEliteTime_02;
    int oldEliteTime_03;
    Matrix matrix;

    int[] eliteTimes;  // record every generation's min time, used for making line chart

    public GaWithGeographicalIsolation(Matrix matrix){

        this.matrix = matrix;
        eliteTimes = new int[1000];
    }

    public int[] process(){

        initChromosome();

        int generations = 0;
        while(generations++!=MAX_EVOLVE_TIME) {

            //every 20 generations, redistribute chromosomes
            if(generations%20 == 0){
                distributeChoromosomes();
            }
 //           System.out.println("generation: " + generations);

            // ensure the elite remains in the population
  //          System.out.println("1:");
            getMinTime(generations,1);
  //          System.out.println("2:");
            getMinTime(generations, 2);
   //         System.out.println("3:");
            getMinTime(generations, 3);

            // get data to make chart
            int tmpEliteTime = Integer.min(oldEliteTime_01,oldEliteTime_02);
            tmpEliteTime = Integer.min(tmpEliteTime,oldEliteTime_03);
            eliteTimes[generations-1] = tmpEliteTime;

            select(1);
            select(2);
            select(3);
            cross(1);
            cross(2);
            cross(3);
            //mutation probability will raise with generation growing
            double prob = MUTATE_PROB*Double.min(generations/50+1,10);
            mutate(prob,1);
            mutate(prob,2);
            mutate(prob,3);
        }
        return eliteTimes;
    }

    private void initChromosome(){

        chromosomes = new int[CHROMOSOME_NUM][TASK_NUM];
        for(int i=0;i<CHROMOSOME_NUM;i++){
            for(int j=0;j<TASK_NUM;j++) {
                chromosomes[i][j] = (int)(Math.random()*MACHINE_NUM);
            }
        }

        //divide all chromosomes into 3 population randomly
        chromosomes_01 = new int[CHROMOSOME_NUM/3][TASK_NUM];
        chromosomes_02 = new int[CHROMOSOME_NUM/3][TASK_NUM];
        chromosomes_03 = new int[CHROMOSOME_NUM/3][TASK_NUM];
        //list to save chromosomes which haven't been allocate
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<CHROMOSOME_NUM;i++){
            list.add(i);
        }
        int position;
        for(int i=0;i<CHROMOSOME_NUM;i++){
            switch (i%3){
                case 0:
                    position = list.get(random.nextInt(list.size()));
                    list.remove(new Integer(position));
                    System.arraycopy(chromosomes[position],0,chromosomes_01[i/3],0,TASK_NUM);
                    break;
                case 1:
                    position = list.get(random.nextInt(list.size()));
                    list.remove(new Integer(position));
                    System.arraycopy(chromosomes[position],0,chromosomes_02[i/3],0,TASK_NUM);
                    break;
                case 2:
                    position = list.get(random.nextInt(list.size()));
                    list.remove(new Integer(position));
                    System.arraycopy(chromosomes[position],0,chromosomes_03[i/3],0,TASK_NUM);
                    break;
                default:
            }
        }

        //init elite
        System.arraycopy(chromosomes_01[0], 0, oldElite_01, 0, TASK_NUM);
        System.arraycopy(chromosomes_02[0], 0, oldElite_02, 0, TASK_NUM);
        System.arraycopy(chromosomes_03[0], 0, oldElite_03, 0, TASK_NUM);

        Machines machines = new Machines();
        for(int i=0;i<TASK_NUM;i++){
            int machineNum = oldElite_01[i];
            machines.machineLatestTime[machineNum] += matrix.matrix[machineNum][i];
        }
        oldEliteTime_01 = 0;
        for(int i=0;i<MACHINE_NUM;i++){
            oldEliteTime_01 = oldEliteTime_01>machines.machineLatestTime[i]?oldEliteTime_01:machines.machineLatestTime[i];
        }

        machines = new Machines();
        for(int i=0;i<TASK_NUM;i++){
            int machineNum = oldElite_02[i];
            machines.machineLatestTime[machineNum] += matrix.matrix[machineNum][i];
        }
        oldEliteTime_02 = 0;
        for(int i=0;i<MACHINE_NUM;i++){
            oldEliteTime_02 = oldEliteTime_02>machines.machineLatestTime[i]?oldEliteTime_02:machines.machineLatestTime[i];
        }

        machines = new Machines();
        for(int i=0;i<TASK_NUM;i++){
            int machineNum = oldElite_03[i];
            machines.machineLatestTime[machineNum] += matrix.matrix[machineNum][i];
        }
        oldEliteTime_03 = 0;
        for(int i=0;i<MACHINE_NUM;i++){
            oldEliteTime_03 = oldEliteTime_03>machines.machineLatestTime[i]?oldEliteTime_03:machines.machineLatestTime[i];
        }
    }

    private void distributeChoromosomes(){

        //merge first
        for(int i=0;i<CHROMOSOME_NUM/3;i++){
            System.arraycopy(chromosomes_01[i],0,chromosomes[i],0,TASK_NUM);
            System.arraycopy(chromosomes_02[i],0,chromosomes[i+CHROMOSOME_NUM/3],0,TASK_NUM);
            System.arraycopy(chromosomes_03[i],0,chromosomes[i+CHROMOSOME_NUM/3*2],0,TASK_NUM);
        }

        //divide randomly
        //list to save chromosomes which haven't been allocate
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<CHROMOSOME_NUM;i++){
            list.add(i);
        }
        int position;
        for(int i=0;i<CHROMOSOME_NUM;i++) {
            switch (i % 3) {
                case 0:
                    position = list.get(random.nextInt(list.size()));
                    list.remove(new Integer(position));
                    System.arraycopy(chromosomes[position], 0, chromosomes_01[i / 3], 0, TASK_NUM);
                    break;
                case 1:
                    position = list.get(random.nextInt(list.size()));
                    list.remove(new Integer(position));
                    System.arraycopy(chromosomes[position], 0, chromosomes_02[i / 3], 0, TASK_NUM);
                    break;
                case 2:
                    position = list.get(random.nextInt(list.size()));
                    list.remove(new Integer(position));
                    System.arraycopy(chromosomes[position], 0, chromosomes_03[i / 3], 0, TASK_NUM);
                    break;
                default:
            }
        }

    }

    private void select(int chromosomeNum){

        //get every task's total process time
        double[] time = countTime(chromosomeNum);

        int[][] tmpChromosome = new int[CHROMOSOME_NUM/3][TASK_NUM];
        switch (chromosomeNum){
            case 1:
                tmpChromosome = chromosomes_01;
                break;
            case 2:
                tmpChromosome = chromosomes_02;
                break;
            case 3:
                tmpChromosome = chromosomes_03;
                break;
            default:
        }

        double total = 0;
        for(int i=0; i<CHROMOSOME_NUM/3; i++){
            total += time[i];
        }
        //get fitness
        double [] fitness = new double[CHROMOSOME_NUM/3];
        for(int i=0;i<CHROMOSOME_NUM/3;i++) {
            if(i==0){
                fitness[i] = total/time[i];
            }else {
                fitness[i] = fitness[i - 1] + total / time[i];
            }
        }
        double totalFitness = fitness[CHROMOSOME_NUM/3-1];
        int[][] offspring = new int[CHROMOSOME_NUM/3][TASK_NUM];
        //select
        for(int i=0;i<CHROMOSOME_NUM/3;i++){
            double tmp = random.nextDouble() * totalFitness;
            for(int j=0;j<CHROMOSOME_NUM/3;j++){
                if(tmp<fitness[j]){
                    System.arraycopy(tmpChromosome[j],0,offspring[i],0,TASK_NUM);
                    break;
                }
            }
        }
        //let offspring replace chromosome
        for(int i=0;i<CHROMOSOME_NUM/3;i++){
            System.arraycopy(offspring[i],0,tmpChromosome[i],0,TASK_NUM);
        }
    }

    /**
     count time of each chromosome
     */
    private double[] countTime(int chromosomeNum){

        int[][] tmpChromosomes = new int[CHROMOSOME_NUM/3][TASK_NUM];

        switch (chromosomeNum){
            case 1:
                tmpChromosomes = chromosomes_01;
                break;
            case 2:
                tmpChromosomes = chromosomes_02;
                break;
            case 3:
                tmpChromosomes = chromosomes_03;
                break;
            default:
        }

        double[] time = new double[CHROMOSOME_NUM/3];
        for(int count=0;count<CHROMOSOME_NUM/3;count++){
            time[count] = 0;
        }
        for(int i=0;i<CHROMOSOME_NUM/3;i++){
            Machines machines = new Machines();
            for(int j=0;j<TASK_NUM;j++){
                int machineNum = tmpChromosomes[i][j];
                machines.machineLatestTime[machineNum] += matrix.matrix[machineNum][j];
            }
            for(int k=0;k<MACHINE_NUM;k++){
                time[i] = time[i]>machines.machineLatestTime[k] ? time[i]:machines.machineLatestTime[k];
            }
        }
        return time;
    }

    private void cross(int chromosomeNum){

        int[][] tmpChromosome = new int[CHROMOSOME_NUM/3][TASK_NUM];
        switch (chromosomeNum){
            case 1:
                tmpChromosome = chromosomes_01;
                break;
            case 2:
                tmpChromosome = chromosomes_02;
                break;
            case 3:
                tmpChromosome = chromosomes_03;
                break;
            default:
        }
        List<Integer> intGroup = new ArrayList<Integer>();
        //put all genes into group, pickup 2 of them each time
        for(int i=0;i<CHROMOSOME_NUM/3;i++){
            intGroup.add(i);
        }
        while(!intGroup.isEmpty()){
            int a = random.nextInt(intGroup.size());
            int first = intGroup.get(a);
            intGroup.remove(a);
            a = random.nextInt(intGroup.size());
            int second = intGroup.get(a);
            intGroup.remove(a);
            if(random.nextDouble()<CROSS_PROB){
                int cutPoint = random.nextInt(TASK_NUM);
                for(int i=cutPoint;i<TASK_NUM;i++){
                    int tmp = tmpChromosome[first][i];
                    tmpChromosome[first][i] = tmpChromosome[second][i];
                    tmpChromosome[second][i] = tmp;
                }
            }
        }
    }

    private void mutate(double prob,int chromosomeNum){

        int[][] tmpChromosome = new int[CHROMOSOME_NUM/3][TASK_NUM];
        switch (chromosomeNum){
            case 1:
                tmpChromosome = chromosomes_01;
                break;
            case 2:
                tmpChromosome = chromosomes_02;
                break;
            case 3:
                tmpChromosome = chromosomes_03;
                break;
            default:
        }
        for(int i=0;i<CHROMOSOME_NUM/3;i++){
            if(random.nextDouble()<prob){
                int pos = random.nextInt(TASK_NUM);
                tmpChromosome[i][pos] = random.nextInt(5);
            }
        }
    }

    private String getMinTime(int generations, int chromosomeNum){

        int[][] tmpChromosomes = new int [CHROMOSOME_NUM/3][TASK_NUM];
        switch (chromosomeNum){
            case 1:
                tmpChromosomes = chromosomes_01;
                break;
            case 2:
                tmpChromosomes = chromosomes_02;
                break;
            case 3:
                tmpChromosomes = chromosomes_03;
                break;
        }
        List<Integer> elites = new ArrayList<Integer>();
        int min = Integer.MAX_VALUE;
        int chr = 0;
        for(int i=0;i<CHROMOSOME_NUM/3;i++){
            int max = 0;
            Machines machines = new Machines();
            for(int j=0;j<TASK_NUM;j++){
                int machine = tmpChromosomes[i][j];
                machines.machineLatestTime[machine] += matrix.matrix[machine][j];
            }
            for(int j=0;j<MACHINE_NUM;j++){
                max = max>machines.machineLatestTime[j]?max:machines.machineLatestTime[j];
            }
            if(min == max){
                elites.add(i);
            }else if(min > max){
                min = max;
                chr = i;
                elites.clear();
                elites.add(i);
            }
        }
        copyElite(elites, min, generations,chromosomeNum);

        return min + "  " + chr;
    }

    private void copyElite(List<Integer> elites, int eliteTime, int generations, int chromosomeNum){

        int tmpOldEliteTime = 0;
        int[] tmpOldElite = new int[TASK_NUM];
        int[][] tmpChromosome = new int[CHROMOSOME_NUM][TASK_NUM];

        switch (chromosomeNum){
            case 1:
                tmpOldEliteTime = oldEliteTime_01;
                tmpOldElite = oldElite_01;
                tmpChromosome = chromosomes_01;
                break;
            case 2:
                tmpOldEliteTime = oldEliteTime_02;
                tmpOldElite = oldElite_02;
                tmpChromosome = chromosomes_02;
                break;
            case 3:
                tmpOldEliteTime = oldEliteTime_03;
                tmpOldElite = oldElite_03;
                tmpChromosome = chromosomes_03;
                break;
        }

        //replace old elite by new elite
        if(tmpOldEliteTime > eliteTime){
//            System.out.println("replace old " + tmpOldEliteTime+ " new" + eliteTime);
            System.arraycopy(tmpChromosome[elites.get(0)], 0, tmpOldElite, 0, TASK_NUM);
            switch (chromosomeNum){
                case 1:
                    oldEliteTime_01 = eliteTime;
                    break;
                case 2:
                    oldEliteTime_02 = eliteTime;
                    break;
                case 3:
                    oldEliteTime_03 = eliteTime;
                    break;
            }
            //copy elite, the number of copies depends on generations, to at most 10
            if(elites.size()<Integer.min(generations/50 + 1,CHROMOSOME_NUM/6)){
//                System.out.println("=======");
                double[] time = countTime(chromosomeNum);
                List<Integer> chromosomeRemains = new ArrayList<Integer>();
                for(int i=0; i<CHROMOSOME_NUM/3; i++){
                    chromosomeRemains.add(i);
                }
                for(int i=elites.size(); i<Integer.min(generations/50+1,CHROMOSOME_NUM/6); i++){
                    double max = 0;
                    int chromosomesToBeReplaced = 0;
                    for(int j:chromosomeRemains){
                        chromosomesToBeReplaced = time[j]>max?j:chromosomesToBeReplaced;
                        max = time[j]>max?time[j]:max;
                    }
                    chromosomeRemains.remove(new Integer(chromosomesToBeReplaced));
                    System.arraycopy(tmpOldElite,0,tmpChromosome[chromosomesToBeReplaced],0,TASK_NUM);
                }
            }
        }

        //replace old elite by new elite
        if(tmpOldEliteTime == eliteTime){
//            System.out.println("equals " + eliteTime);
            //copy elite, the number of copies depends on generations, to at most 10
            if(elites.size()<Integer.min(generations/50 + 1,CHROMOSOME_NUM/6)){
                double[] time = countTime(chromosomeNum);
                List<Integer> chromosomeRemains = new ArrayList<Integer>();
                for(int i=0; i<CHROMOSOME_NUM/3; i++){
                    chromosomeRemains.add(i);
                }
                for(int i=elites.size(); i<Integer.min(generations/50+1,CHROMOSOME_NUM/6); i++){
                    double max = 0;
                    int chromosomesToBeReplaced = 0;
                    for(int j:chromosomeRemains){
                        chromosomesToBeReplaced = time[j]>max?j:chromosomesToBeReplaced;
                        max = time[j]>max?time[j]:max;
                    }
                    chromosomeRemains.remove(new Integer(chromosomesToBeReplaced));
                    System.arraycopy(tmpOldElite,0,tmpChromosome[chromosomesToBeReplaced],0,TASK_NUM);
                }
            }
        }

        //old elite remains the same
        if(tmpOldEliteTime < eliteTime){
//            System.out.println("remain old"+ tmpOldEliteTime+ " new" + eliteTime);
            double[] time = countTime(chromosomeNum);
            List<Integer> chromosomeRemains = new ArrayList<Integer>();
            for(int i=0; i<CHROMOSOME_NUM/6; i++){
                chromosomeRemains.add(i);
            }
            //replace the worst chromosomes by old elite, to most 5
            for(int i=1; i<Integer.min(generations / 50 + 1, CHROMOSOME_NUM/6); i++){
                double max = 0;
                int chromosomesToBeReplaced = chromosomeRemains.get(0);
                for(int j:chromosomeRemains){
                    chromosomesToBeReplaced = time[j]>max ? j:chromosomesToBeReplaced;
                    max = time[j]>max?time[j]:max;
                }
                chromosomeRemains.remove(new Integer(chromosomesToBeReplaced));
                System.arraycopy(tmpOldElite, 0, tmpChromosome[chromosomesToBeReplaced], 0, TASK_NUM);
            }
        }
    }
}
