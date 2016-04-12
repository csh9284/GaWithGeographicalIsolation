import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonard on 2015/6/11.
 */
public class Tasks {

    public List<Integer> taskUnassigned;     //存放未分配的task集合

    public Tasks(){

        taskUnassigned = new ArrayList<Integer>();
        for (int i=0;i<Constants.TASK_NUM;i++){
            taskUnassigned.add(i);
        }
    }
}
