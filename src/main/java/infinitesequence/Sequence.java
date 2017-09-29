package infinitesequence;

import java.math.BigInteger;
import java.util.Scanner;

public class Sequence {
    private static int seqWidthLimit;
    private static StringBuilder sequence = new StringBuilder();
    private static String subSequence;
    private static long curPosition = 1;

    public static void main(String[] args) {
        BigInteger nextNum;
        Integer digit;
        boolean found = false;

        subSequence = getSubsequence(); //получаем искомую последовательность
        setSeqWidthLimit(subSequence.length()); //выставляем ограничение на ширину бесконечной последовательности
        for(BigInteger i = BigInteger.ZERO; i.compareTo(BigInteger.valueOf(1000000)) < 0 && !found; i = i.add(BigInteger.ONE)){
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
            curPosition++;
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
}
