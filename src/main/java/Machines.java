/**
 * Created by leonard on 2015/6/10.
 */
public class Machines {

    public int[] machineLatestTime;

    public Machines(){

        machineLatestTime = new int[Constants.MACHINE_NUM];  //存放机器完成已分配任务的时间
        for (int i=0;i<5;i++){
            machineLatestTime[i] = 0;
        }
    }
}
