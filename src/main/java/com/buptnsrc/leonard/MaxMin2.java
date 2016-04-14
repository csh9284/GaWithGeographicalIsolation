package com.buptnsrc.leonard;

import com.buptnsrc.leonard.Machines;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonard on 16-3-31.
 */
public class MaxMin2 {

    Matrix matrix;
    int[] chromosome;

    private int TASK_NUM = Constants.TASK_NUM;
    private int MACHINE_NUM = Constants.MACHINE_NUM;

    public MaxMin2(Matrix matrix){

        this.matrix = matrix;
        chromosome = new int[TASK_NUM];
        for(int i=0;i<TASK_NUM;i++){
            chromosome[i] = -1;
        }
    }

    public int process(){

        List<Integer> taskUnassigned = new ArrayList<Integer>();
        for(int i=0;i<TASK_NUM;i++){
            taskUnassigned.add(i);
        }
        Machines machines = new Machines();

        while(!taskUnassigned.isEmpty()){

            int max = 0;
            int taskToBeAssigned = 0;
            int machineToBeAssigned = 0;
            for(int i=0; i<taskUnassigned.size(); i++){
                int taskNum = taskUnassigned.get(i);
                int min = Integer.MAX_VALUE;
                int tmpMachine = 0;
                for(int j=0;j<MACHINE_NUM;j++){
                    int time = machines.machineLatestTime[j] + matrix.matrix[j][i];
                    tmpMachine = min<time ? tmpMachine:j;
                    min = min<time ? min:time;
                }
                machineToBeAssigned = max>min ? machineToBeAssigned:tmpMachine;
                taskToBeAssigned = max>min ? taskToBeAssigned:taskNum;
                max = max>min ? max:min;
            }

            machines.machineLatestTime[machineToBeAssigned] += matrix.matrix[machineToBeAssigned][taskToBeAssigned];
            chromosome[taskToBeAssigned] = machineToBeAssigned;
            taskUnassigned.remove(new Integer(taskToBeAssigned));
        }

        int max = Integer.MIN_VALUE;
        for(int i=0;i<MACHINE_NUM;i++){
            max = max > machines.machineLatestTime[i] ? max : machines.machineLatestTime[i];
        }
        return max;

 //       System.out.println("assign:");

//        List<Integer>[] machineList = new List[com.buptnsrc.leonard.Constants.MACHINE_NUM];
//        for (int i=0;i<com.buptnsrc.leonard.Constants.MACHINE_NUM;i++) {
//            machineList[i] = new ArrayList<Integer>();
//        }
//
//        for(int i=0;i<5;i++){
//            for(int j=0;j<20;j++){
//                if(chromosome[j] == i){
//                    machineList[i].add(j);
//                }
//            }
//        }
//        for (int i=0;i<com.buptnsrc.leonard.Constants.MACHINE_NUM;i++){
//            System.out.println("machine" + i + " " + machineList[i].toString());
//        }
    }
}
