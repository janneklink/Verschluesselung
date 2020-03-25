package des;

import java.util.Arrays;
import java.util.List;

public class TextBlock64Bit extends BitBlock {
    final byte[] textblock64;
    List<List<Integer>> initialPermutation;
    List<List<Integer>> finalPermutation;

    public TextBlock64Bit(byte[] cleartext) {
        checkSize(NumberOfBytes.TEXTBLOCK64BIT.length, cleartext.length);
        this.textblock64 = cleartext;
    }

    public Textblock32Bit getLBlock() {
        byte[] lBlock = Arrays.copyOfRange(textblock64, 0, NumberOfBytes.TEXTBLOCK32BIT.length);
        return new Textblock32Bit(lBlock);
    }

    public Textblock32Bit getRBlock() {
        byte[] rBlock = Arrays.copyOfRange(textblock64, NumberOfBytes.TEXTBLOCK32BIT.length, 2 * NumberOfBytes.TEXTBLOCK32BIT.length);
        return new Textblock32Bit(rBlock);
    }

    public TextBlock64Bit getInitialPermutation() {
        initializeInitialPermutation();
        byte[] permutatedText = extractNewBytes(textblock64, NumberOfBytes.TEXTBLOCK64BIT, initialPermutation);
        return new TextBlock64Bit(permutatedText);
    }

    public TextBlock64Bit getFinalPermutation() {
        initializeFinalPermutation();
        byte[] permutatedText = extractNewBytes(textblock64, NumberOfBytes.TEXTBLOCK64BIT, finalPermutation);
        return new TextBlock64Bit(permutatedText);
    }

    private void initializeInitialPermutation() {
        List<Integer> byte0 = List.of(58, 50, 42, 34, 26, 18, 10, 2);
        List<Integer> byte1 = List.of(60, 52, 44, 36, 28, 20, 12, 4);
        List<Integer> byte2 = List.of(62, 54, 46, 38, 30, 22, 14, 6);
        List<Integer> byte3 = List.of(64, 56, 48, 40, 32, 24, 16, 8);
        List<Integer> byte4 = List.of(57, 49, 41, 33, 25, 17, 9, 1);
        List<Integer> byte5 = List.of(59, 51, 43, 35, 27, 19, 11, 3);
        List<Integer> byte6 = List.of(61, 53, 45, 37, 29, 21, 13, 5);
        List<Integer> byte7 = List.of(63, 55, 47, 39, 31, 23, 15, 7);
        initialPermutation = List.of(byte0, byte1, byte2, byte3, byte4, byte5, byte6, byte7);
    }

    private void initializeFinalPermutation() {
        List<Integer> byte0 = List.of(40, 8, 48, 16, 56, 24, 64, 32);
        List<Integer> byte1 = List.of(39, 7, 47, 15, 55, 23, 63, 31);
        List<Integer> byte2 = List.of(38, 6, 46, 14, 54, 22, 62, 30);
        List<Integer> byte3 = List.of(37, 5, 45, 13, 53, 21, 61, 29);
        List<Integer> byte4 = List.of(36, 4, 44, 12, 52, 20, 60, 28);
        List<Integer> byte5 = List.of(35, 3, 43, 11, 51, 19, 59, 27);
        List<Integer> byte6 = List.of(34, 2, 42, 10, 50, 18, 58, 26);
        List<Integer> byte7 = List.of(33, 1, 41, 9, 49, 17, 57, 25);
        finalPermutation = List.of(byte0, byte1, byte2, byte3, byte4, byte5, byte6, byte7);
    }

}
