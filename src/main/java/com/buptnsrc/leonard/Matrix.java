package com.buptnsrc.leonard;

import com.buptnsrc.leonard.Constants;

/**
 * Created by leonard on 2015/6/10.
 */
public class Matrix {

    public int[][] matrix;

    public Matrix(){

    }

    public Matrix(int x){

        matrix = new int[Constants.MACHINE_NUM][Constants.TASK_NUM];               //10行50列矩阵，存放每个task在对应机器上的执行时间

//        if(x==0){
//
//            for (int j=0; j<com.buptnsrc.leonard.Constants.TASK_NUM; j++){
//
//                double taskTime = Math.random() * 1000;
//                for (int i=0; i<com.buptnsrc.leonard.Constants.MACHINE_NUM; i++){
//
//                    matrix[i][j] = taskTime + Math.random()*100;
//                    taskTime = matrix[i][j];
//                }
//            }
//        }
//
//        if(x==1){
//
//            for (int j=0; j<2500; j++){
//
//                double taskTime = Math.random() * 1000;
//                for (int i=0; i<20; i++){
//                    matrix[i][j] = taskTime + Math.random()*1000;
//                    taskTime = matrix[i][j];
//                }
//            }
//            for (int j=2500; j<5000; j++){
//
//                double taskTime = Math.random() * 30000;
//                for (int i=0; i<20; i++){
//                    matrix[i][j] = taskTime + Math.random()*30000;
//                    taskTime = matrix[i][j];
//                }
//            }
//        }
//
//        if(x==2){
//
//            for (int j=0; j<4500; j++){
//
//                double taskTime = Math.random() * 1000;
//                for (int i=0; i<20; i++){
//                    matrix[i][j] = taskTime + Math.random()*1000;
//                    taskTime = matrix[i][j];
//                }
//            }
//            for (int j=4500; j<5000; j++){
//
//                double taskTime = Math.random() * 30000;
//                for (int i=0; i<20; i++){
//                    matrix[i][j] = taskTime + Math.random()*30000;
//                    taskTime = matrix[i][j];
//                }
//            }
//        }
//
//        if(x==3){
//
//            for (int j=0; j<500; j++){
//
//                double taskTime = Math.random() * 1000;
//                for (int i=0; i<20; i++){
//                    matrix[i][j] = taskTime + Math.random()*1000;
//                    taskTime = matrix[i][j];
//                }
//            }
//            for (int j=500; j<5000; j++){
//
//                double taskTime = Math.random() * 30000;
//                for (int i=0; i<20; i++){
//                    matrix[i][j] = taskTime + Math.random()*30000;
//                    taskTime = matrix[i][j];
//                }
//            }
//        }
//
//        if(x==4){
//            for (int j=0; j<5000; j++){
//                for(int i=0; i<20; i++){
//                    matrix[i][j] = Math.random() * 1000;
//                }
//            }
//        }
//
//    }
//
//    public void showMatrix(){
//        for (int j=0;j<5000;j++){
//            for (int i=0;i<20;i++){
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
//
//    public void updateMatrix(int i, int j, double k){
//        if(i<20 && j<5000){
//            this.matrix[i][j] = k;
//        }else{
//            System.out.println("Not valid input");
//        }
    }
}
