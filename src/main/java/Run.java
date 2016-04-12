/**
 * Created by leonard on 2015/6/11.
 */
public class Run {

    public static void main(String[] args){

        Matrix matrix = new Matrix();
        matrix.matrix = new int[5][30];
        matrix.matrix[0][0] = (int) (Math.random()*100);
        matrix.matrix[0][1] = (int) (Math.random()*100);
        matrix.matrix[0][2] = (int) (Math.random()*100);
        matrix.matrix[0][3] = (int) (Math.random()*100);
        matrix.matrix[0][4] = (int) (Math.random()*100);
        matrix.matrix[0][5] = (int) (Math.random()*100);
        matrix.matrix[0][6] = (int) (Math.random()*100);
        matrix.matrix[0][7] = (int) (Math.random()*100);
        matrix.matrix[0][8] = (int) (Math.random()*100);
        matrix.matrix[0][9] = (int) (Math.random()*100);
        matrix.matrix[0][10] = (int) (Math.random()*100);
        matrix.matrix[0][11] = (int) (Math.random()*100);
        matrix.matrix[0][12] = (int) (Math.random()*100);
        matrix.matrix[0][13] = (int) (Math.random()*100);
        matrix.matrix[0][14] = (int) (Math.random()*100);
        matrix.matrix[0][15] = (int) (Math.random()*100);
        matrix.matrix[0][16] = (int) (Math.random()*100);
        matrix.matrix[0][17] = (int) (Math.random()*100);
        matrix.matrix[0][18] = (int) (Math.random()*100);
        matrix.matrix[0][19] = (int) (Math.random()*100);
        matrix.matrix[0][20] = (int) (Math.random()*100);
        matrix.matrix[0][21] = (int) (Math.random()*100);
        matrix.matrix[0][22] = (int) (Math.random()*100);
        matrix.matrix[0][23] = (int) (Math.random()*100);
        matrix.matrix[0][24] = (int) (Math.random()*100);
        matrix.matrix[0][25] = (int) (Math.random()*100);
        matrix.matrix[0][26] = (int) (Math.random()*100);
        matrix.matrix[0][27] = (int) (Math.random()*100);
        matrix.matrix[0][28] = (int) (Math.random()*100);
        matrix.matrix[0][29] = (int) (Math.random()*100);
        for(int i=1;i<5;i++){
            for(int j=0;j<30;j++){
                matrix.matrix[i][j] = matrix.matrix[i-1][j] + (int)(matrix.matrix[i-1][j] * Math.random()*0.2 + 1);
            }
        }
        for(int i=0;i<5;i++){
            for(int j=0;j<30;j++){
                System.out.print(matrix.matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        GaWithGeographicalIsolation ga2 = new GaWithGeographicalIsolation(matrix);
        ga2.process();
        System.out.println();
        ClassicGa ga = new ClassicGa(matrix);
        System.out.print("ga:");
        ga.process();
        MinMin min = new MinMin(matrix);
        System.out.println("minmin");
        min.process();
        System.out.println("maxmin2");
        MaxMin2 maxMin2 = new MaxMin2(matrix);
        maxMin2.process();
    }
}

/*
save matrix

        Matrix matrix = new Matrix();
        matrix.matrix = new int[5][20];
        matrix.matrix[0][0] = 13;
        matrix.matrix[0][1] = 15;
        matrix.matrix[0][2] = 6;
        matrix.matrix[0][3] = 20;
        matrix.matrix[0][4] = 32;
        matrix.matrix[0][5] = 16;
        matrix.matrix[0][6] = 23;
        matrix.matrix[0][7] = 22;
        matrix.matrix[0][8] = 50;
        matrix.matrix[0][9] = 30;
        matrix.matrix[0][10] = 7;
        matrix.matrix[0][11] = 11;
        matrix.matrix[0][12] = 15;
        matrix.matrix[0][13] = 14;
        matrix.matrix[0][14] = 23;
        matrix.matrix[0][15] = 27;
        matrix.matrix[0][16] = 31;
        matrix.matrix[0][17] = 54;
        matrix.matrix[0][18] = 63;
        matrix.matrix[0][19] = 40;

        matrix.matrix[i][j] = matrix.matrix[i-1][j] + (int)(Math.random()*3 + 1);
*/

