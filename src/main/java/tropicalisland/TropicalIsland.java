package tropicalisland;

import java.util.Random;

public class TropicalIsland {
    private static final int H_MAX = 1000;
    private static final int N_MAX = 50;
    private static final int M_MAX = 50;

    public static void main(String[] args) {
        TropicalIsland instance = new TropicalIsland();
        Integer[][] island = instance.getIsland();
        int waterVolume = instance.getWaterVolume(island);
        System.out.println(waterVolume);
    }

    public int getWaterVolume(Integer[][] island) {
        //TODO
        return 0;
    }

    //auxiliary methods
    public void printIsland(Integer[][] island) {
        for (int row = 0; row < island.length; row++) {
            for (int col = 0; col < island[row].length; col++) {
                System.out.printf("%4d\t", island[row][col]);
            }
            System.out.println();
        }
    }

    public Integer[][] getIsland() {
        Random random = new Random();
        //int N = 3;
        //int M = 5;
        int N = random.nextInt(N_MAX) + 1;
        int M = random.nextInt(M_MAX) + 1;
        Integer[][] island = new Integer[N][M];

        for(int row = 0; row < N; row++){
            for(int col = 0; col < M; col++){
                island[row][col] = random.nextInt(H_MAX) + 1;
            }
        }

        return island;
    }
}
