package TrickySequence;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileWriter;
import java.math.BigInteger;

public class MethodsTests {
    private static final BigInteger nothingWasFound = BigInteger.valueOf(-1);
    private static InfiniteSequence infiniteSequence = new InfiniteSequence();

    @Test
    public void testGetNextNumForSequence(){
        BigInteger result = infiniteSequence.getNextNumForSequence(BigInteger.ZERO);

        Assert.assertEquals(BigInteger.ONE, result);

        for(BigInteger i = BigInteger.ZERO; i.compareTo(BigInteger.TEN) < 0; i = i.add(BigInteger.ONE)){
            result = infiniteSequence.getNextNumForSequence(i);
        }
        Assert.assertEquals(BigInteger.TEN, result);

        //Use this to create a text file with infinite sequence. Use MS Notepad to search across the text a FON.
       /* try{
            FileWriter writer = new FileWriter("temp.txt", false);
            for(BigInteger i = BigInteger.valueOf(0); i.compareTo(BigInteger.valueOf(33334104)) < 0; i = i.add(BigInteger.ONE)){
                result = infiniteSequence.getNextNumForSequence(i);
                writer.write(result.toString());

                if(i.mod(BigInteger.valueOf(10000)).equals(BigInteger.ZERO)){
                    writer.flush();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        */
    }

    @Ignore
    public void testNumbersInRangeCount(){
        int counter = 0;
        for(int i = 1000000; i < 10000000; i++){
            counter++;
        }

        System.out.println(counter);
    }

    @Test
    public void testGetDigitsBeforeNumber(){
        Assert.assertEquals(BigInteger.valueOf(5), infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(5)));
        Assert.assertEquals(BigInteger.valueOf(20), infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(15)));
        Assert.assertEquals(BigInteger.valueOf(188), infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(99)));
        Assert.assertEquals(BigInteger.valueOf(2887), infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(999)));
        Assert.assertEquals(BigInteger.valueOf(325), infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(145)));
        Assert.assertEquals(BigInteger.valueOf(1693), infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(601)));
        Assert.assertEquals(BigInteger.valueOf(190), infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(100)));
        Assert.assertEquals(BigInteger.valueOf(1), infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(1)));
        Assert.assertEquals(BigInteger.valueOf(2392), infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(834)));

        System.out.println(infiniteSequence.getDigitsBeforeNumber(BigInteger.valueOf(114890)));
    }

    @Test
    public void testGetDigitsInNumber(){
        BigInteger number = BigInteger.valueOf(3492183710L);

        int digitsInNumber = infiniteSequence.howManyDigitsInNumber(number);

        Assert.assertEquals(10, digitsInNumber);
    }

    @Test
    public void testGetFON_SequenceOf_n_zeros_TenTo_n_thPower(){
        String sequence = "00000";
        Assert.assertEquals(BigInteger.valueOf(100000), infiniteSequence.getFONifSeqConsistsOfZeros(sequence));

        sequence = "0";
        Assert.assertEquals(BigInteger.valueOf(10), infiniteSequence.getFONifSeqConsistsOfZeros(sequence));

        sequence = "00040";
        Assert.assertEquals(nothingWasFound, infiniteSequence.getFONifSeqConsistsOfZeros(sequence));
    }

    @Test
    public void testGetFONsplitAndShuffle(){
        String subSequence = "4465";
        BigInteger result = infiniteSequence.getFONsplitAndShuffle(subSequence);
        Assert.assertEquals(BigInteger.valueOf(4654), result);

        subSequence = "18765432";
        result = infiniteSequence.getFONsplitAndShuffle(subSequence);
        Assert.assertEquals(BigInteger.valueOf(21876543), result);

        subSequence = "000400";
        result = infiniteSequence.getFONsplitAndShuffle(subSequence);
        Assert.assertEquals(BigInteger.valueOf(4000), result);
    }

    @Test
    public void testGetFONuniqueNumber(){
        String sequence = "3564536";
        BigInteger result = infiniteSequence.getFONuniqueNumber(sequence);
        Assert.assertEquals(BigInteger.valueOf(3564536), result);
    }

    @Test
    public void testGenerateSeqStartingFromNum(){
        String startingNum = "4567";
        Integer minLength = 13;

        String result = infiniteSequence.generateSeqStartingFromNum(startingNum, minLength);

        Assert.assertEquals("45674568456945704571", result);
    }

    @Test
    public void testDoesGeneratedSeqContainSubSeq(){
        String sequence = "4567456845694570";
        String subSequence = "6945";

        Assert.assertTrue(infiniteSequence.doesGeneratedSeqContainSubSeq(sequence, subSequence));

        subSequence = "111";
        Assert.assertFalse(infiniteSequence.doesGeneratedSeqContainSubSeq(sequence, subSequence));
    }

    @Test
    public void testGetFONcombineDigitsIntoNumLeftToRight(){
        String subSequence = "0001000050";
        BigInteger result = infiniteSequence.getFONcombineDigitsIntoNumLeftToRight(subSequence);
        Assert.assertEquals(nothingWasFound, result);

        subSequence = "4465";
        result = infiniteSequence.getFONcombineDigitsIntoNumLeftToRight(subSequence);
        Assert.assertEquals(BigInteger.valueOf(464), result);

        subSequence = "4555";
        result = infiniteSequence.getFONcombineDigitsIntoNumLeftToRight(subSequence);
        Assert.assertEquals(BigInteger.valueOf(53), result);

        subSequence = "113472";
        result = infiniteSequence.getFONcombineDigitsIntoNumLeftToRight(subSequence);
        Assert.assertEquals(BigInteger.valueOf(13471), result);
    }

    @Test
    public void testShiftBackBeginning(){
        String seqBeginning = "56";
        int emptyPos = 5;
        String result = infiniteSequence.shiftBackBeginning(seqBeginning, emptyPos);
        Assert.assertEquals("53", result);

        seqBeginning = "3";
        emptyPos = 7;
        result = infiniteSequence.shiftBackBeginning(seqBeginning, emptyPos);
        Assert.assertEquals("-1", result);
    }

    @Test
    public void testGetFONrearRightMinusOneGoesToLeft(){
        String subSequence = "9994";
        BigInteger result = infiniteSequence.getFONrearRightMinusOneGoesToLeft(subSequence);
        Assert.assertEquals(BigInteger.valueOf(3999), result);

        subSequence = "9990";
        result = infiniteSequence.getFONrearRightMinusOneGoesToLeft(subSequence);
        Assert.assertEquals(nothingWasFound, result);

        subSequence = "441";
        result = infiniteSequence.getFONrearRightMinusOneGoesToLeft(subSequence);
        Assert.assertEquals(nothingWasFound, result);

        subSequence = "94";
        result = infiniteSequence.getFONrearRightMinusOneGoesToLeft(subSequence);
        Assert.assertEquals(BigInteger.valueOf(39), result);
    }

    @Test
    public void testGetFONcombineDigitsIntoNumRightToLeft(){
        String subSequence = "3480";
        BigInteger result = infiniteSequence.getFONcombineDigitsIntoNumRightToLeft(subSequence);
        Assert.assertEquals(BigInteger.valueOf(4802), result);

        subSequence = "3483";
        result = infiniteSequence.getFONcombineDigitsIntoNumRightToLeft(subSequence);
        Assert.assertEquals(BigInteger.valueOf(833), result);

        subSequence = "19472";
        result = infiniteSequence.getFONcombineDigitsIntoNumRightToLeft(subSequence);
        Assert.assertEquals(BigInteger.valueOf(4719), result);
    }

    @Test
    public void testGetSubSeqRelativePos(){
        String sequence = "7891011";
        String subSequence = "910";
        int result = infiniteSequence.getSubSeqRelativePos(sequence, subSequence);
        Assert.assertEquals(2, result);

        sequence = "3333456";
        subSequence = "3456";
        result = infiniteSequence.getSubSeqRelativePos(sequence, subSequence);
        Assert.assertEquals(3, result);

        sequence = "3334533456";
        subSequence = "3456";
        result = infiniteSequence.getSubSeqRelativePos(sequence, subSequence);
        Assert.assertEquals(6, result);

        sequence = "10000";
        subSequence = "0000";
        result = infiniteSequence.getSubSeqRelativePos(sequence, subSequence);
        Assert.assertEquals(1, result);

        sequence = "99";
        subSequence = "99";
        result = infiniteSequence.getSubSeqRelativePos(sequence, subSequence);
        Assert.assertEquals(0, result);
    }

    @Test
    public void testGetFONpositionOverlap(){
        String sequence = "934832934";
        BigInteger result = infiniteSequence.getFONpositionOverlap(sequence);
        Assert.assertEquals(BigInteger.valueOf(293483), result);

        sequence = "332543253";
        result = infiniteSequence.getFONpositionOverlap(sequence);
        Assert.assertEquals(BigInteger.valueOf(25332543), result);
    }
}
