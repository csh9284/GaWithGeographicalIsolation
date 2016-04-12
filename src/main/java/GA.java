import java.util.ArrayList;
import java.util.List;

/**
 * Created by leonard on 2015/6/16.
 */
public class GA {


    public static final int MAX_EVOLVE_TIME = 1000;
    public static final int CHROMOSOME_NUM = 100;
    public static final int MACHINE_NUM = Constants.MACHINE_NUM;
    public static final int TASK_NUM = Constants.TASK_NUM;

    Matrix matrix;
    int[][] chromosomes;

    public GA(Matrix matrix){

        this.matrix = matrix;
        chromosomes = new int[CHROMOSOME_NUM][TASK_NUM];
    }

    public void process(){

        /*initial*/
        this.createChromosomes();
        int evolveTimeLimit = MAX_EVOLVE_TIME;
        int[] elite = getElite();
        int eliteRemainTheSame = 0;

        int abc=0;
        /*begin to process*/
        while(evolveTimeLimit-- >= 0 && eliteRemainTheSame<150){ //begin to evolve
            abc++;

            /*check end condition*/
            if(chromosomesConverge()){ //if all chromosomes are the same, end evolve
                System.out.println("converge" + abc);
                break;
            }
            eliteRemainTheSame = judgeSameElite(elite, eliteRemainTheSame);
            if(eliteRemainTheSame == 0){
                elite = getElite();
            }

            /* start to evolve */
            this.selection();
            this.crossover();
            this.mutation();
        }

        showResult();
    }

    private void createChromosomes(){

        MinMin minmin = new MinMin(matrix);
        for (int i=0; i<CHROMOSOME_NUM; i++){
            for(int j=0; j<TASK_NUM; j++){
                chromosomes[i][j] = (int) (Math.random()*MACHINE_NUM);
            }
        }
    }

    private int[] getElite(){

        int[] elite = new int[TASK_NUM];
        double finishTime = Double.MAX_VALUE;
        for(int i=0; i<CHROMOSOME_NUM; i++){
            if(getFinishTime(chromosomes[i]) < finishTime){
                elite = chromosomes[i];
                finishTime = getMakeSpan(elite);
            }
        }
        return elite;
    }

    private boolean chromosomesConverge(){

        int i=1;
        while(i < CHROMOSOME_NUM){
            for(int j=0; j<TASK_NUM; j++){
                if(chromosomes[0][j] != chromosomes[i][j]){
                    return false;
                }
            }
            i++;
        }
        return true;
    }

    private int judgeSameElite(int[] elite, int eliteSameTimes){

        int[] eliteTmp = getElite();
        if(this.chromosomesEquals(elite, eliteTmp)){
            return eliteSameTimes + 1;
        }else{
            return 0;
        }
    }

    private boolean chromosomesEquals(int[] chromosome_1, int[] chromosome_2){

        for(int i=0; i<TASK_NUM; i++){
            if(chromosome_1[i] != chromosome_2[i]){
                return false;
            }
        }
        return true;
    }

    private double getFinishTime(int[] chromosome){

        double[] machines = new double[MACHINE_NUM]; //machine execute time
        for(int i=0; i<MACHINE_NUM; i++){
            machines[i] = 0;
        }
        double latest = 0;
        for (int i=0; i<TASK_NUM; i++){
            machines[chromosome[i]] = machines[chromosome[i]] + matrix.matrix[chromosome[i]][i];
        }
        for(int i=0; i<MACHINE_NUM; i++){
            if(machines[i] > latest){
                latest = machines[i];
            }
        }
        return latest;
    }

    private double getMakeSpan(int[] chromosome){

        double[] machines = new double[MACHINE_NUM]; //machine execute time
        for(int i=0; i<MACHINE_NUM; i++){
            machines[i] = 0;
        }
        double earliest = Double.MAX_VALUE;
        double latest = 0;
        for (int i=0; i<TASK_NUM; i++){
            machines[chromosome[i]] = machines[chromosome[i]] + matrix.matrix[chromosome[i]][i];
        }
        for(int i=0; i<MACHINE_NUM; i++){
            if(machines[i] < earliest){
                earliest = machines[i];
            }
            if(machines[i] > latest){
                latest = machines[i];
            }
        }
        return latest-earliest;
    }

    private void selection(){

        double[] weight = getWeight(); //get weight of each chromosome in order to perform the selection
        int[][] juniorGeneration = new int[CHROMOSOME_NUM][TASK_NUM]; //put the new generation
        for(int i=0; i<CHROMOSOME_NUM; i++){
            juniorGeneration[i] = chromosomes[rouletteDice(weight)];
        }
        chromosomes = juniorGeneration;
    }

    private double[] getWeight(){

        double totalTime = 0;
        for(int i=0; i<CHROMOSOME_NUM; i++){
            totalTime = totalTime + getFinishTime(chromosomes[i]);
        }

        double weight[] = new double[CHROMOSOME_NUM];
        for(int i=0; i<CHROMOSOME_NUM; i++){
            if(i==0) {
                weight[i] = totalTime / getMakeSpan(chromosomes[i]);
            }
            else{
                weight[i] = (totalTime / getMakeSpan(chromosomes[i])) + weight[i-1];
            }
        }

        return weight;
    }

    private int rouletteDice(double weight[]){
        
        double dice = Math.random() * weight[CHROMOSOME_NUM-1];
        for(int i=0; i<CHROMOSOME_NUM; i++){
            if(dice < weight[i]){
                return i;
            }
        }
        return CHROMOSOME_NUM-1;
    }

    private void crossover(){

        int[] flag = initFlag();
        int i = (int) (CHROMOSOME_NUM * 0.5);
        while(i-- > 0){
            int chromosome_1 = generateCrossUnit(flag);
            flag[chromosome_1] = -1;
            int chromosome_2 = generateCrossUnit(flag);
            flag[chromosome_2] = -1;
            cross(chromosome_1, chromosome_2);
        }
    }

    private int[] initFlag(){

        int[] flag;
        flag = new int[CHROMOSOME_NUM];
        for(int i=0; i<CHROMOSOME_NUM; i++){
            flag[i] = 0;
        }
        return flag;
    }

    private int generateCrossUnit(int[] flag){

        int a = (int) (Math.random() * CHROMOSOME_NUM);
        if(flag[a] != -1){
            return a;
        }else {
            return generateCrossUnit(flag);
        }
    }

    private void cross(int chromosome_1, int chromosome_2){

        if(Math.random() < 0.6){
            return;
        }
        int firstPoint = (int) (Math.random() * TASK_NUM);
        int secondPoint = (int) (Math.random() * TASK_NUM);
        for(int i = firstPoint<=secondPoint?firstPoint:secondPoint; i <= (firstPoint<=secondPoint?secondPoint:firstPoint); i++){
            int tmp = chromosomes[chromosome_1][i];
            chromosomes[chromosome_1][i] = chromosomes[chromosome_2][i];
            chromosomes[chromosome_2][i] = tmp;
        }
    }

    private void mutation(){

        int i = (int) (CHROMOSOME_NUM);
        while(i-->0){
            if(Math.random()>0.05){
                continue;
            }
            int chromosomeToBeMutated = i;
            int taskToBeMutated = (int) (Math.random() * TASK_NUM);
            int machineToBeMutated = (int) (Math.random() * MACHINE_NUM);
            chromosomes[chromosomeToBeMutated][taskToBeMutated] = machineToBeMutated;
        }
    }

    private void showResult(){

        int[] elite = getElite();
        double[] finishTimes = new double[MACHINE_NUM];
        for(int i=0; i<MACHINE_NUM; i++){
            finishTimes[i] = 0;
        }
        for(int i=0; i<TASK_NUM; i++){
            finishTimes[elite[i]] = finishTimes[elite[i]] + matrix.matrix[elite[i]][i];
        }
        double max = 0;
        double min = Double.MAX_VALUE;
        for(int i=0; i<MACHINE_NUM; i++){
            max = max>finishTimes[i]?max:finishTimes[i];
            min = min<finishTimes[i]?min:finishTimes[i];
        }
        System.out.println("finish time:" + max);
        System.out.println("span" + (max-min));
        List<Integer>[] machineList = new List[MACHINE_NUM];
        for (int i=0;i<MACHINE_NUM;i++) {
            machineList[i] = new ArrayList<Integer>();
        }
        for(int i=0; i<TASK_NUM; i++){
            machineList[elite[i]].add(i);
        }
        System.out.println("assign: ");
        for (int i = 0; i < MACHINE_NUM; i++) {
            System.out.println("machine" + i + " " + machineList[i].toString());
        }
    }
}
