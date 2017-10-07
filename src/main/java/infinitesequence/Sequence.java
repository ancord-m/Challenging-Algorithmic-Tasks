package infinitesequence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sequence {
    private static int seqWidthLimit;
    private static StringBuilder sequence = new StringBuilder();
    private static String subSequence;
    private static BigInteger curPosition = BigInteger.ONE;
    private static BigInteger nothingWasFound = BigInteger.valueOf(-1);
    private static final BigInteger NUMBER_LIMIT = new BigInteger("100000000000000000000");

    public static void main(String[] args) {
        BigInteger nextNum;
        Integer digit;
        boolean found = false;

        subSequence = getSubsequence(); //получаем искомую последовательность
        setSeqWidthLimit(subSequence.length()); //выставляем ограничение на ширину бесконечной последовательности
        for(BigInteger i = BigInteger.ZERO; i.compareTo(NUMBER_LIMIT) < 0 && !found; i = i.add(BigInteger.ONE)){
            nextNum = getNextNumForSequence(i);
            for(int j = 0; j < nextNum.toString().length(); j++) {
                digit = Character.getNumericValue(nextNum.toString().charAt(j));
                sequence = updateSequence(sequence, digit, seqWidthLimit);
                found = areSeqAndSubseqEqual(subSequence, sequence.toString());

                if(found){
                    System.out.println("Found");
                    System.out.println("Subsequence: " + subSequence);
                    System.out.println("Sequence: " + sequence);
                    System.out.println("First occurance at position " + curPosition);
                    break;
                }
            }

            if(i.mod(BigInteger.valueOf(1000000)).equals(BigInteger.ZERO)){
                System.out.println("Passed number " + i);
            }
        }
    }

    static String getSubsequence(){
        String result;
        System.out.print("Введите искомую последовательность цифр: ");
        Scanner sc = new Scanner(System.in);
        result = sc.nextLine();
        //TODO оставлять только цифры
        return result;
    }


    /*
    Bellow are the methods used for different ways of reconstruction of ordinal numbers.
    surrounding the provided sequence from the stdin are presented below.
    Almost each method looks for several different sequences of ordinal numbers
    and when these numbers are concatenated together they form the sequence to be found.
    To be more exact, the sequence of numbers contains the sequence to be found.
    NOTE ABOUT RETURN VALUE. Everywhere.
    As there can be found several variants of concatenated ordinal numbers which are yield the desired result
    each method will return the smallest ordinal number starting with whom the sequence to be found can be generated.
    "-1" will be returned in the case when nothing was found.
    P.S. FON - First(Smallest) Ordinal Number
     */

    /**
     * CASE #1
     * Starting from left we are collecting digits and treat them as numbers, reconstructing the ordinal number row
     * E.g. we have 1945:
     * - picking up 1 and generating a sequence: 1234
     * - 19 -> 1920
     * - 194 -> 194195
     * - 1945 -> it can be treated as a unique number, but, probably here we just engrossing CASE #8
     * - starting from second digit 9 -> 891011 (ATTENTION! we've started from 2nd digit, so we need to make step back!)
     * - adding third digit, 95 -> 949596
     * - 945 -> 944945946, moving further
     * - 4 -> 324567
     * - 45 -> 444546
     * - last digit is 5 -> 2345678
     * so we have generated many sequences: 1234, 1920, 194195, 891011, 949596, 944945946, 324567, 444546, 2345678
     * which of them contains the desired 1945 ? There's no such, but it could be there.
     * @return -1 or least first ordinal number
     */
    static BigInteger getFONcombineDigitsIntoNumLeftToRight(final String subSequence){
        List<BigInteger> possibleFONs = new ArrayList<BigInteger>();

        int length = subSequence.length();
        for(int startFrom = 0; startFrom < length; startFrom++){
            //omit everything starting from zero
            if(subSequence.charAt(startFrom) == '0'){
                continue;
            }
            //at each iteration a possible beginning of a sequence emerges
            for(int digQuant = 1; digQuant <= (length - startFrom); digQuant++){
                //this gives a possible beginning combined from digits of subsequence
                String seqBeginning =
                        subSequence.substring(startFrom, startFrom + digQuant);
                //decreasing the beginning to cover opened left-side positions
                if(startFrom > 0){
                    seqBeginning = shiftBackBeginning(seqBeginning, startFrom);
                }
                //if we dropped into negative area then there's no sense to continue
                if(seqBeginning.equals(nothingWasFound.toString())){
                    continue;
                }

                if(doesGeneratedSeqContainSubSeq(
                        generateSeqStartingFromNum(seqBeginning, subSequence.length()), subSequence)) {
                    possibleFONs.add(new BigInteger(seqBeginning));
                }
            }
        }

        if(possibleFONs.isEmpty()){
            return nothingWasFound;
        }

        return findMinimalPossibleFON(possibleFONs);
    }

    /**
     * CASE #6
     * If we asked to find the first appearance of zero-sequence, e.g. 0000
     * the answer is very easy to find. It is obvious that 0000 won't be found
     * earlier than inside the ordinal number 10000. So one just needs to count
     * a quantity of zeros (n) and rise 10 to n-th power and that will be the FON answer.
     * @param subSequence
     * @return
     */
    static BigInteger getFONifSeqConsistsOfZeros(final String subSequence){
        //check that subSequence contains only zeros
        for(int i = 0; i < subSequence.length(); i++){
            if(subSequence.charAt(i) != '0'){
                return nothingWasFound;
            }
        }

        return BigInteger.TEN.pow(subSequence.length());
    }

    /**
     * CASE #4, #9
     * The idea of this method is to split a subSequence into two parts and shuffle it
     * in the way so that the right part will be the beginning of an ordinal number, and the left part - the end.
     * E.g. we've got 4465
     * let's split it (dot is the splitter):
     * 1) 4.465 -> 465 goes to the left, 4 goes to the right,
     * thus we have two ordinal numbers 4654 4655, and there is 4456 subsequence.
     * It's first appearance is somewhere after 4654, not bad.
     * 2) 44.65 -> 6544 and 6545, again we have 4465, but it will be met after 6544,
     * and 6544 is greater than 4654. Seems to be bad.
     * 3) 446.5 -> 5446 5447, the appearance will happen after 5446.
     *
     * So splitting and shuffling two parts of the provided sequence yields
     * three approximate first appearances: 4654, 6544, 5446.
     * It is obvious that 4654 is the FON (first ordinal number) here, because
     * it gives one the earliest appearance of 4465: 46544655
     *
     * P.S. Indeed, the right answer is 464 and 465, but that's the result of another method.
     */
    static BigInteger getFONsplitAndShuffle(final String subSequence){
        List<BigInteger> possibleFONs = new ArrayList<BigInteger>();

        //split and shuffle
        for(int i = 1; i < subSequence.length(); i++){
            StringBuilder number = new StringBuilder();
            number.append(subSequence.substring(i));
            number.append(subSequence.substring(0, i));
            //1. Generate a sequence from number got above longer than subSequence
            //2. Check whether seq contains subSeq or not
            //3. If true, put into possibleFONs
            if(doesGeneratedSeqContainSubSeq(
                    generateSeqStartingFromNum(number.toString(), subSequence.length()), subSequence)) {
                possibleFONs.add(new BigInteger(number.toString()));
            }
        }

        BigInteger result = findMinimalPossibleFON(possibleFONs);

        return result;
    }

    /**
     * CASE #8
     * Well, even more simple case than the story with all zeros.
     * The subsequence at input can be treated as a standalone ordinal number
     * and probably it's first digit appearance is the answer to the main question of the task.
     * E.g. 1000.
     * @param subSequence
     * @return
     */
    static BigInteger getFONuniqueNumber(final String subSequence){
        return new BigInteger(subSequence);
    }

    //Below are the auxiliary methods

    /**
     * !!ONE OF THE MAIN METHODS!!
     * Calculates the sum of all digits of all numbers which are smaller than a given number PLUS ONE.
     * Plus one gives us the "position" of the the first digit of the given number in the infinite
     * sequence of digits.
     *
     * now calculating how many digits in the numerical intervals
     * 100...999
     * 10...99
     * 1..9
     * so we know that currently we have e.g. 3 digits in number, for example number 145
     * below it there are numbers with one digit 1..9 and with two digits 10..99
     * one just needs to add to the result got above the following:
     * 9 * 10 to (n-1) * n
     * where n - is the quantity of digits in numbers from current interval
     * again, e.g. for 145 we calculate, that before it 45 numbers with n = 3 digits, so 3*45 = 135 digits
     * going lower, n = 2, we are inside 10..99 interval
     * 9 * 10^1 * 2 = 90 (numbers) * 2 (digits in each) = 180
     * again, lower, n = 1
     * 9 * 10^0 * 1 = 9
     * the result equals 324
     * @param number a positive integer
     * @return
     */
    static BigInteger getDigitsBeforeNumber(BigInteger number){
        BigInteger NINE = BigInteger.valueOf(9);
        int digitsInNum = howManyDigitsInNumber(number); //how many digits in the NUMBER

        //how many numbers with the same digits quantity are before NUMBER, e.g 15 - 10 = 5 (10, 11, 12, 13, 14)
        // NUMBER itself NOT INCLUDED!
        long multiplier = (long)Math.pow(10, --digitsInNum);
        BigInteger result = BigInteger.valueOf(multiplier);
        result = number.subtract(result);
        //how many digits in all these numbers, e.g (10, 11, 12, 13, 14) totally have 10 digits
        result = result.multiply(BigInteger.valueOf(digitsInNum + 1));

        //summing other digits
        while (digitsInNum != 0){
            result = result.add(
                    NINE.multiply(BigInteger.TEN.pow(--digitsInNum)).multiply(BigInteger.valueOf(digitsInNum + 1)));
        }

        return result.add(BigInteger.ONE);
    }

    /**
     * Give it any number, and this method will try to reconstruct ordinal numbers
     * which are surround the given subSequence. Length should be greater than subSeq length.
     * Cool side effect: leading zeros in the number-string will be omitted during conversion into a BigInteger.
     * NOTE: keep an eye opened at while condition. Can it lead to a wrong return value?
     * E.g. we are looking for 000400 (length = 6), splitting and shuffling gives 400.000 (length = 6)
     * generating with "<" condition gives us 400000, while with "<=" gives us 400000400001.
     * In the last case the generated sequence contains 000400 while the first one doesn't.
     */
    static String generateSeqStartingFromNum(final String number, final Integer minLength){
        StringBuilder result = new StringBuilder();
        BigInteger biNumber = new BigInteger(number);

        while(result.length() <= minLength) {
            result.append(biNumber);
            biNumber = getNextNumForSequence(biNumber);
        }

        return result.toString();
    }

    static boolean doesGeneratedSeqContainSubSeq(final String generatedSeq, final String subSeq){
        return generatedSeq.contains(subSeq);
    }

    static int howManyDigitsInNumber(BigInteger number){
        BigInteger temp = new BigInteger(number.toString());
        int digitsInNum = 0;
        while (!temp.equals(BigInteger.ZERO)) {
            temp = temp.divide(BigInteger.TEN);
            digitsInNum++;
        }
        return digitsInNum;
    }

    static BigInteger getNextNumForSequence(BigInteger initial){
        return initial.add(BigInteger.ONE);
    }

    static BigInteger getPreviousNumForSequence(BigInteger initial){
        return initial.subtract(BigInteger.ONE);
    }

    //Old methods for straight forward and time consuming solution

    static void setSeqWidthLimit(int width){
        seqWidthLimit = width;
    }

    static StringBuilder updateSequence(StringBuilder sequence, Integer digit, int seqWidth){
        StringBuilder result;

        if(sequence.length() >= seqWidth) {
            result = new StringBuilder(sequence.substring(1));
            result.append(digit);
            curPosition = curPosition.add(BigInteger.ONE);
        } else {
            result = sequence.append(digit);
        }

        return result;
    }

    static boolean areSeqAndSubseqEqual(String subSequence, String sequence){
        if(subSequence.length() != sequence.length()){
            return false;
        }

        for(int i = 0; i < subSequence.length(); i++){
            if(subSequence.charAt(i) != sequence.charAt(i)){
                return false;
            }
        }
        return true;
    }

    /**
     * If we have a sequence 723014458 and standing at 7th position and taking two digits, we have number 58 and treating it as a beginning of a sequence.
     * We want to reconstruct the sequence. We can't just increase 58, because we have 7 positions at the left
     * we need to decrease 58: 57, 56, 55.. but how many times? The answer is: quantity of all digits during decreasing 58 must exceed here 7 postions.
     * @param seqBeginning The possible beginning, e.g 58
     * @param emptyPositions how many positions must be closed by digits at the left side of a sequence to be reconstructed
     * @return new decreased beginning, e.g. 54. This gives us 5455565758, so we've reconstructed everything before 58
     */
    static String shiftBackBeginning(String seqBeginning, int emptyPositions){
        BigInteger newBeginning = new BigInteger(seqBeginning);
        int coveredBack = 0;

        while(coveredBack <= emptyPositions){
            newBeginning = getPreviousNumForSequence(newBeginning);
            coveredBack += howManyDigitsInNumber(newBeginning);
        }

        if(newBeginning.compareTo(BigInteger.ZERO) == -1) {
            return nothingWasFound.toString();
        }

        return newBeginning.toString();
    }

    static BigInteger findMinimalPossibleFON(List<BigInteger> possibleFONs) {
        //looking for a minimal value
        BigInteger result = BigInteger.valueOf(Long.MAX_VALUE);
        for (BigInteger bi : possibleFONs) {
            if (bi.compareTo(result) == -1) {
                result = bi;
            }
        }
        return result;
    }
}
