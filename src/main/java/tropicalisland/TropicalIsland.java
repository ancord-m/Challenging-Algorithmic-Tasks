package tropicalisland;

import java.util.Random;

public class TropicalIsland {
    private static final int H_MAX = 9;
    private static final int N_MAX = 5;
    private static final int M_MAX = 5;

    private Integer[][] W;

    public static void main(String[] args) {
        TropicalIsland instance = new TropicalIsland();
        Integer[][] island = instance.getIsland();

        System.out.println("Before rain: ");
        instance.printIsland(island);

        //instance.initW(island);
        int waterVolume = instance.getWaterVolume(island);
        System.out.println(waterVolume);
    }

    public int getWaterVolume(Integer[][] island) {
        W = initW(island);

        for(int row = 0; row < island.length; row++){
            islandWalker(island, 0, row, 0);
        }

        for(int row = 0; row < island.length; row++){
            islandWalker(island, island[0].length, row, 0);
        }

        for(int col = 0; col < island[0].length; col++){
            islandWalker(island, col, 0, 0);
        }

        for(int col = 0; col < island[0].length; col++){
            islandWalker(island, col, island.length, 0);
        }

        System.out.println("After rain: ");
        printIsland(W);

        return 0;
    }

    void islandWalker(Integer[][] island, int row, int col, int height){
        if(row < 0 || col < 0 || row >= N_MAX  || col >= M_MAX ){
            return;
        }

        if(W[row][col] <= height){
            return;
        }

        if(height < island[row][col]){
            height = island[row][col];
        }
        W[row][col] = height;

        islandWalker(island, row-1, col, height);
        islandWalker(island, row+1, col, height);
        islandWalker(island, row, col-1, height);
        islandWalker(island, row, col+1, height);
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
        int N = 5;
        int M = 5;
        //int N = random.nextInt(N_MAX) + 1;
        //int M = random.nextInt(M_MAX) + 1;
        Integer[][] island = new Integer[N][M];

        for(int row = 0; row < N; row++){
            for(int col = 0; col < M; col++){
                island[row][col] = random.nextInt(H_MAX) + 1;
            }
        }

        return island;
    }

    Integer[][] initW(Integer[][] island){
        Integer[][] W = new Integer[island.length][island[0].length];
        for(int row = 0; row < W.length; row++){
            for(int col = 0; col < W[row].length; col++){
                W[row][col] = H_MAX + 1;
            }
        }

        return W;
    }
}
