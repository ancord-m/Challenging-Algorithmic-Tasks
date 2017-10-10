package tropicalisland;

import java.util.Random;

public class TropicalIsland {
    private static final int H_MAX = 1000; //max height
    private static final int N_MAX = 50;  //max row quantity
    private static final int M_MAX = 50;  //max column quantity

    private Integer[][] islandAfterRain;

    public static void main(String[] args) {
        TropicalIsland instance = new TropicalIsland();

        Integer[][] island = instance.getIsland();
        int waterVolume = instance.getWaterVolume(island);

        System.out.println(waterVolume);
    }

    public int getWaterVolume(Integer[][] island) {
        initIslandAfterRain(island);

        for(int row = 0; row < island.length; row++){
            islandBackTracker(island, 0, row, 0);
        }

        for(int row = 0; row < island.length; row++){
            islandBackTracker(island, island[0].length - 1, row, 0);
        }

        for(int col = 0; col < island[0].length; col++){
            islandBackTracker(island, col, 0, 0);
        }

        for(int col = 0; col < island[0].length; col++){
            islandBackTracker(island, col, island.length - 1, 0);
        }

        return calcAfterRainWaterVolume(island);
    }

    void islandBackTracker(Integer[][] island, int row, int col, int height){
        //the main recurrence relations
        if(row < 0 || col < 0 || row >= island.length  || col >= island[0].length ){
            return;
        }

        if(islandAfterRain[row][col] <= height){
            return;
        }

        if(height < island[row][col]){
            height = island[row][col];
        }
        islandAfterRain[row][col] = height;

        islandBackTracker(island, row-1, col, height);
        islandBackTracker(island, row+1, col, height);
        islandBackTracker(island, row, col-1, height);
        islandBackTracker(island, row, col+1, height);
    }

    //auxiliary methods
    void printIsland(Integer[][] island) {
        for (int row = 0; row < island.length; row++) {
            for (int col = 0; col < island[row].length; col++) {
                System.out.printf("%4d\t", island[row][col]);
            }
            System.out.println();
        }
    }

    Integer[][] getIsland() {
        Random random = new Random();
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

    void initIslandAfterRain(Integer[][] island){
        islandAfterRain = new Integer[island.length][island[0].length];
        for(int row = 0; row < islandAfterRain.length; row++){
            for(int col = 0; col < islandAfterRain[0].length; col++){
                islandAfterRain[row][col] = H_MAX + 1;
            }
        }
    }

    int calcAfterRainWaterVolume(Integer[][] islandBeforeRain){
        int result = 0;

        for(int row = 0; row < islandBeforeRain.length; row++){
            for(int col = 0; col < islandBeforeRain[0].length; col++){
                result += islandAfterRain[row][col] - islandBeforeRain[row][col];
            }
        }

        return result;
    }
}
