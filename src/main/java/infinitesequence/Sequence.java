package infinitesequence;

import java.math.BigInteger;
import java.util.Scanner;

public class Sequence {
    private static int seqWidthLimit;
    private static StringBuilder sequence = new StringBuilder();
    private static String subSequence;
    private static BigInteger curPosition = BigInteger.ONE;
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

    static void setSeqWidthLimit(int width){
        seqWidthLimit = width;
    }

    static BigInteger getNextNumForSequence(BigInteger initial){
        return initial.add(BigInteger.ONE);
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
        int digitsInNum = getDigitsInNumber(number); //how many digits in the NUMBER

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

    static int getDigitsInNumber(BigInteger number){
        BigInteger temp = new BigInteger(number.toString());
        int digitsInNum = 0;
        while (!temp.equals(BigInteger.ZERO)) {
            temp = temp.divide(BigInteger.TEN);
            digitsInNum++;
        }
        return digitsInNum;
    }
}
