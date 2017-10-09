package tropicalisland;

import org.junit.Assert;
import org.junit.Test;

public class IslandTests {
    private static TropicalIsland instance = new TropicalIsland();

    //The main test-case! Add islands here to check whether the answer is correct or not!
    @Test
    public void testGetWaterVolume() {
        {
            Integer[][] island =
                    {
                            {4, 5, 4},
                            {3, 1, 5},
                            {5, 4, 1}
                    };
            Assert.assertEquals(2, instance.getWaterVolume(island));
        }
        {
            Integer[][] island =
                    {
                            {5, 3, 4, 5},
                            {6, 2, 1, 4},
                            {3, 1, 1, 4},
                            {8, 5, 4, 3}
                    };
            Assert.assertEquals(7, instance.getWaterVolume(island));
        }
        {
            Integer[][] island =
                    {
                            {2, 2, 2},
                            {2, 1, 2},
                            {2, 1, 2},
                            {2, 1, 2}
                    };
            Assert.assertEquals(0, instance.getWaterVolume(island));
        }
    }

    //simple tests
    @Test
    public void testGenerateIsland(){
        Integer[][] island = instance.getIsland();
        instance.printIsland(island);
    }
}
