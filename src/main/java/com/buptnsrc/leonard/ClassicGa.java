package com.buptnsrc.leonard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by leonard on 16-3-29.
 */
public class ClassicGa {

    private final int MAX_EVOLVE_TIME = 1000;
    private final int CHROMOSOME_NUM = 30;
    private final double CROSS_PROB = 0.6;
    private final double MUTATE_PROB = 0.005;
    private final int MACHINE_NUM = Constants.MACHINE_NUM;
    private final int TASK_NUM = Constants.TASK_NUM;
    public static Random random = new Random(new Date().getTime());

    int[] oldElite = new int[TASK_NUM];
    int oldEliteTime;
    int[][] chromosomes;

    int[] eliteTimes; // save elite time of every generation, used for making chart

    Matrix matrix;

    public ClassicGa(Matrix matrix){
        this.matrix = matrix;
        eliteTimes = new int[1000];
    }

    public int[] process(){

        initChromosome();

        int generations = 0;
        while(generations++!=MAX_EVOLVE_TIME) {

//            System.out.println("generation: " + generations);

            // ensure the elite remains in the population
            getMinTime(generations);
            eliteTimes[generations-1] = oldEliteTime;

            select();
            cross();
            //mutation probability will raise with generation growing
            double prob = MUTATE_PROB*Double.min(generations/50+1,10);
            mutate(prob);
        }
        return eliteTimes;
    }

    private void initChromosome(){

        chromosomes = ChromosomeInitializer.getChromosomes();

        //init elite
        System.arraycopy(chromosomes[0],0,oldElite,0,TASK_NUM);
        Machines machines = new Machines();
        for(int i=0;i<TASK_NUM;i++){
            int machineNum = oldElite[i];
            machines.machineLatestTime[machineNum] += matrix.matrix[machineNum][i];
        }
        oldEliteTime = 0;
        for(int i=0;i<MACHINE_NUM;i++){
            oldEliteTime = oldEliteTime>machines.machineLatestTime[i]?oldEliteTime:machines.machineLatestTime[i];
        }
    }

    private void select(){

        //get every task's total process time
        double[] time = countTime();
        double total = 0;
        for(int i=0; i<CHROMOSOME_NUM; i++){
            total += time[i];
        }
        //get fitness
        double [] fitness = new double[CHROMOSOME_NUM];
        for(int i=0;i<CHROMOSOME_NUM;i++) {
            if(i==0){
                fitness[i] = total/time[i];
            }else {
                fitness[i] = fitness[i - 1] + total / time[i];
            }
        }
        double totalFitness = fitness[CHROMOSOME_NUM-1];
        int[][] offspring = new int[CHROMOSOME_NUM][TASK_NUM];
        //select
        for(int i=0;i<CHROMOSOME_NUM;i++){
            double tmp = random.nextDouble() * totalFitness;
            for(int j=0;j<CHROMOSOME_NUM;j++){
                if(tmp<fitness[j]){
                    System.arraycopy(chromosomes[j],0,offspring[i],0,TASK_NUM);
                    break;
                }
            }
        }
        //let offspring replace chromosome
        for(int i=0;i<CHROMOSOME_NUM;i++){
                System.arraycopy(offspring[i],0,chromosomes[i],0,TASK_NUM);
        }
    }

    /**
        count time of each chromosome
     */
    private double[] countTime(){
        double[] time = new double[CHROMOSOME_NUM];
        for(int count=0;count<CHROMOSOME_NUM;count++){
            time[count] = 0;
        }
        for(int i=0;i<CHROMOSOME_NUM;i++){
            Machines machines = new Machines();
            for(int j=0;j<TASK_NUM;j++){
                int machineNum = chromosomes[i][j];
                machines.machineLatestTime[machineNum] += matrix.matrix[machineNum][j];
            }
            for(int k=0;k<MACHINE_NUM;k++){
                time[i] = time[i]>machines.machineLatestTime[k] ? time[i]:machines.machineLatestTime[k];
            }
        }
        return time;
    }

    private void cross(){

        List<Integer> intGroup = new ArrayList<Integer>();
        //put all genes into group, pickup 2 of them each time
        for(int i=0;i<CHROMOSOME_NUM;i++){
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
                    int tmp = chromosomes[first][i];
                    chromosomes[first][i] = chromosomes[second][i];
                    chromosomes[second][i] = tmp;
                }
            }
        }
    }

    private void mutate(double prob){

        for(int i=0;i<CHROMOSOME_NUM;i++){
            if(random.nextDouble()<prob){
                int pos = random.nextInt(TASK_NUM);
                chromosomes[i][pos] = random.nextInt(5);
            }
        }
    }

    private String getMinTime(int generations){

        List<Integer> elites = new ArrayList<Integer>();
        int min = Integer.MAX_VALUE;
        int chr = 0;
        for(int i=0;i<CHROMOSOME_NUM;i++){
            int max = 0;
            Machines machines = new Machines();
            for(int j=0;j<TASK_NUM;j++){
                int machine = chromosomes[i][j];
                machines.machineLatestTime[machine] += matrix.matrix[machine][j];
            }
            for(int j=0;j<MACHINE_NUM;j++){
                max = max>machines.machineLatestTime[j]?max:machines.machineLatestTime[j];
            }
            if(min == max){
                elites.add(i);
            }else if(min > max){
                min = max;
                elites.clear();
                elites.add(i);
                chr = i;
            }
        }
        copyElite(elites, min, generations);
        return min + "  " + chr;
    }

    private void copyElite(List<Integer> elites, int eliteTime, int generations){

        //replace old elite by new elite
        if(oldEliteTime > eliteTime){
//            System.out.println("replace old"+ " old" + oldEliteTime+ " new" + eliteTime);
            System.arraycopy(chromosomes[elites.get(0)],0,oldElite,0,TASK_NUM);
            oldEliteTime = eliteTime;
            //copy elite, the number of copies depends on generations, to at most 10
            if(elites.size()<Integer.min(generations/50 + 1,10)){
                double[] time = countTime();
                List<Integer> chromosomeRemains = new ArrayList<Integer>();
                for(int i=0; i<CHROMOSOME_NUM; i++){
                    chromosomeRemains.add(i);
                }
                for(int i=elites.size(); i<Integer.min(generations/50+1,10); i++){
                    double max = 0;
                    int chromosomesToBeReplaced = 0;
                    for(int j:chromosomeRemains){
                        chromosomesToBeReplaced = time[j]>max?j:chromosomesToBeReplaced;
                        max = time[j]>max?time[j]:max;
                    }
                    chromosomeRemains.remove(new Integer(chromosomesToBeReplaced));
                    System.arraycopy(oldElite,0,chromosomes[chromosomesToBeReplaced],0,TASK_NUM);
                }
            }
        }

        //replace old elite by new elite
        if(oldEliteTime == eliteTime){
//            System.out.println("equals " + eliteTime);
            //copy elite, the number of copies depends on generations, to at most 10
            if(elites.size()<Integer.min(generations/50 + 1,10)){
                double[] time = countTime();
                List<Integer> chromosomeRemains = new ArrayList<Integer>();
                for(int i=0; i< CHROMOSOME_NUM; i++){
                    chromosomeRemains.add(i);
                }
                for(int i=elites.size(); i<Integer.min(generations/50+1,10); i++){
                    double max = 0;
                    int chromosomesToBeReplaced = 0;
                    for(int j:chromosomeRemains){
                        chromosomesToBeReplaced = time[j]>max?j:chromosomesToBeReplaced;
                        max = time[j]>max?time[j]:max;
                    }
                    chromosomeRemains.remove(new Integer(chromosomesToBeReplaced));
                    System.arraycopy(oldElite,0,chromosomes[chromosomesToBeReplaced],0,TASK_NUM);
                }
            }
        }

        //old elite remains the same
        if(oldEliteTime < eliteTime){
 //           System.out.println("remain old"+ " old" + oldEliteTime+ " new" + eliteTime);
            double[] time = countTime();
            List<Integer> chromosomeRemains = new ArrayList<Integer>();
            for(int i=0; i<CHROMOSOME_NUM; i++){  //todo bug
                chromosomeRemains.add(i);
            }
            //replace the worst chromosomes by old elite, to most 5
            for(int i=1; i<Integer.min(generations / 100 + 1, 5); i++){
                double max = 0;
                int chromosomesToBeReplaced = chromosomeRemains.get(0);
                for(int j:chromosomeRemains){
                    chromosomesToBeReplaced = time[j]>max ? j:chromosomesToBeReplaced;
                    max = time[j]>max?time[j]:max;
                }
                chromosomeRemains.remove(new Integer(chromosomesToBeReplaced));
                System.arraycopy(oldElite, 0, chromosomes[chromosomesToBeReplaced], 0, TASK_NUM);
            }
        }
    }
}
