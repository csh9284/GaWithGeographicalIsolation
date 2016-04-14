package com.buptnsrc.leonard;

import com.buptnsrc.leonard.Machines;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonard on 2015/6/11.
 */
public class MinMin {

    private int TASK_NUM = Constants.TASK_NUM;
    private int MACHINE_NUM = Constants.MACHINE_NUM;

    Matrix matrix;

    private int[] chromosome = new int[TASK_NUM];

    public MinMin(Matrix matrix){

        this.matrix = matrix;
        for(int i=0;i<TASK_NUM;i++){
            chromosome[i] = -1;
        }
    }

    public int process() {

        List<Integer> tasks = new ArrayList<Integer>();
        for(int i=0;i<TASK_NUM;i++){
            tasks.add(i);
        }
        Machines machines = new Machines();

        while(!tasks.isEmpty()){

            int minTime = Integer.MAX_VALUE; //which task to assign
            int taskToBeAssigned = 0;
            int machineToBeAssigned = 0;
            for(int i=0; i<tasks.size(); i++){
                int taskNum = tasks.get(i);
                int min = Integer.MAX_VALUE;
                int tmpMachine = 0;
                for(int j=0;j<MACHINE_NUM;j++){
                    int time = machines.machineLatestTime[j] + matrix.matrix[j][taskNum];
                    tmpMachine = min < time ? tmpMachine : j;
                    min = min < time ? min : time;
                }
                taskToBeAssigned = minTime < min ? taskToBeAssigned:taskNum;
                machineToBeAssigned = minTime < min ? machineToBeAssigned : tmpMachine;
                minTime = minTime < min ? minTime:min;
            }

            machines.machineLatestTime[machineToBeAssigned] += matrix.matrix[machineToBeAssigned][taskToBeAssigned];
            tasks.remove(new Integer(taskToBeAssigned));
            chromosome[taskToBeAssigned] = machineToBeAssigned;
        }

        int max = Integer.MIN_VALUE;
        for(int i=0;i<MACHINE_NUM;i++){
            max = max > machines.machineLatestTime[i] ? max : machines.machineLatestTime[i];
        }

        return max;
    }
}
