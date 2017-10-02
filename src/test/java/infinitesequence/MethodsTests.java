package infinitesequence;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class MethodsTests {
    @Test
    public void testGetNextNumForSequence(){
        BigInteger result = Sequence.getNextNumForSequence(BigInteger.ZERO);

        Assert.assertEquals(BigInteger.ONE, result);

        for(BigInteger i = BigInteger.ZERO; i.compareTo(BigInteger.TEN) < 0; i = i.add(BigInteger.ONE)){
            result = Sequence.getNextNumForSequence(i);
        }
        Assert.assertEquals(BigInteger.TEN, result);
    }

    @Test
    public void testUpdateSequence_AddOneDigit_LimitNotReached(){
        Integer digit = 5;
        int limitOfSequenceWidth = 5;
        Long digitsBehind = new Long(1);
        StringBuilder seq = new StringBuilder("123");

        StringBuilder result = Sequence.updateSequence(seq, digit, limitOfSequenceWidth);

        Assert.assertEquals("1235", result.toString());
        Assert.assertEquals(1, digitsBehind.intValue());
    }

    @Test
    public void testUpdateSequence_AddOneDigit_LimitReached(){
        Integer digit = 5;
        int limitOfSequenceWidth = 3;
        StringBuilder seq = new StringBuilder("123");

        StringBuilder result = Sequence.updateSequence(seq, digit, limitOfSequenceWidth);

        Assert.assertEquals("235", result.toString());
    }

    @Test
    public void testUpdateSequence_AddManyDigit_LimitNotReached(){
        Integer digit;
        Integer nextNum = 471;
        int limitOfSequenceWidth = 10;
        Long digitsBehind = new Long(1);
        StringBuilder sequence = new StringBuilder();
        StringBuilder expectedResult = new StringBuilder(sequence);

        for(int i = 0; i < nextNum.toString().length(); i++){
            digit = Character.getNumericValue(nextNum.toString().charAt(i));
            sequence = Sequence.updateSequence(sequence, digit, limitOfSequenceWidth);
            System.out.println(sequence);

            expectedResult.append(digit);

            Assert.assertEquals(expectedResult.toString(), sequence.toString());
        }

        Assert.assertEquals(1, digitsBehind.intValue());
    }

    @Test
    public void testUpdateSequence_AddManyDigit_LimitReached(){
        Integer digit;
        Integer nextNum = 45678910;
        int limitOfSequenceWidth = 5;

        StringBuilder sequence = new StringBuilder();
        StringBuilder expectedResult = new StringBuilder(sequence);

        for(int i = 0; i < nextNum.toString().length(); i++){
            digit = Character.getNumericValue(nextNum.toString().charAt(i));
            sequence = Sequence.updateSequence(sequence, digit, limitOfSequenceWidth);
            System.out.println(sequence);

            expectedResult.append(digit);
            if(expectedResult.length() > limitOfSequenceWidth){
                expectedResult = new StringBuilder(expectedResult.substring(1));
            }

            Assert.assertEquals(expectedResult.toString(), sequence.toString());
        }
    }

    @Test
    public void testAreSeqAndSubseqEqual_SameAtInput_True(){
        String subSequence = "41567";
        String sequence = "41567";

        Assert.assertTrue(Sequence.areSeqAndSubseqEqual(subSequence, sequence));
    }

    @Test
    public void testAreSeqAndSubseqEqual_DiffAtInput_False(){
        String subSequence = "41561";
        String sequence = "41567";

        Assert.assertFalse(Sequence.areSeqAndSubseqEqual(subSequence, sequence));
    }

    @Test
    public void testNumbersInRangeCount(){
        int counter = 0;
        for(int i = 1000000; i < 10000000; i++){
            counter++;
        }

        System.out.println(counter);
    }

    @Test
    public void testGetDigitsBeforeNumber(){
        Assert.assertEquals(BigInteger.valueOf(5), Sequence.getDigitsBeforeNumber(BigInteger.valueOf(5)));
        Assert.assertEquals(BigInteger.valueOf(20), Sequence.getDigitsBeforeNumber(BigInteger.valueOf(15)));
        Assert.assertEquals(BigInteger.valueOf(188), Sequence.getDigitsBeforeNumber(BigInteger.valueOf(99)));
        Assert.assertEquals(BigInteger.valueOf(2887), Sequence.getDigitsBeforeNumber(BigInteger.valueOf(999)));
        Assert.assertEquals(BigInteger.valueOf(325), Sequence.getDigitsBeforeNumber(BigInteger.valueOf(145)));
        Assert.assertEquals(BigInteger.valueOf(1693), Sequence.getDigitsBeforeNumber(BigInteger.valueOf(601)));
        Assert.assertEquals(BigInteger.valueOf(190), Sequence.getDigitsBeforeNumber(BigInteger.valueOf(100)));
        Assert.assertEquals(BigInteger.valueOf(1), Sequence.getDigitsBeforeNumber(BigInteger.valueOf(1)));
    }

    @Test
    public void testGetDigitsInNumber(){
        BigInteger number = BigInteger.valueOf(3492183710L);

        int digitsInNumber = Sequence.getDigitsInNumber(number);

        Assert.assertEquals(10, digitsInNumber);
    }
}
