import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonard on 2015/6/10.
 */
public class MaxMin{

    Matrix matrix;
    Machines machines;
    Tasks tasks;

    public MaxMin(Matrix matrix){

        this.matrix = matrix;
        machines = new Machines();
        tasks = new Tasks();
    }

    public void process(){

        List<Integer>[] machineList = new List[Constants.MACHINE_NUM];
        for (int i=0;i<Constants.MACHINE_NUM;i++) {
            machineList[i] = new ArrayList<Integer>();
        }

        while(!tasks.taskUnassigned.isEmpty()){
            double maxTimeForAllTask = 0;//存放最大的最短完成时间
            double minTimeForOneTask;//存放某task的最短完成时间
            int machineToBeAssigned = -1;
            int taskToBeRemoved = -1;

            for (Integer each : tasks.taskUnassigned){
                int machine = -1;
                minTimeForOneTask = Double.MAX_VALUE;
                for (int i = each,j=0;j<Constants.MACHINE_NUM;j++){
                    machine = matrix.matrix[j][i] + machines.machineLatestTime[j] < minTimeForOneTask ? j:machine;
                    minTimeForOneTask = matrix.matrix[j][i] + machines.machineLatestTime[j] < minTimeForOneTask ? matrix.matrix[j][i] + machines.machineLatestTime[j] : minTimeForOneTask;
                }//for循环完毕后，min为某任务最早完成时间
                machineToBeAssigned =  maxTimeForAllTask>minTimeForOneTask?machineToBeAssigned:machine;
                taskToBeRemoved = maxTimeForAllTask>minTimeForOneTask?taskToBeRemoved:each;
                maxTimeForAllTask = maxTimeForAllTask>minTimeForOneTask?maxTimeForAllTask:minTimeForOneTask;
            }

            machines.machineLatestTime[machineToBeAssigned] = machines.machineLatestTime[machineToBeAssigned] + matrix.matrix[machineToBeAssigned][taskToBeRemoved];
            tasks.taskUnassigned.remove(new Integer(taskToBeRemoved));
            machineList[machineToBeAssigned].add(taskToBeRemoved);

            System.out.print(taskToBeRemoved+" ");
        }

        System.out.println();

        double finishTime = 0;
        double firstFinishTime = Double.MAX_VALUE;
        for (int i=0;i<Constants.MACHINE_NUM;i++){
            finishTime = machines.machineLatestTime[i] > finishTime ? machines.machineLatestTime[i] : finishTime;
            firstFinishTime = machines.machineLatestTime[i] < firstFinishTime ? machines.machineLatestTime[i] : firstFinishTime;
        }
        System.out.println("finish time : " + finishTime);
        System.out.println("span: " + (finishTime - firstFinishTime));
        System.out.println("assign: ");
        for (int i=0;i<Constants.MACHINE_NUM;i++){
            System.out.println("machine" + i + " " + machineList[i].toString());
        }
    }
}