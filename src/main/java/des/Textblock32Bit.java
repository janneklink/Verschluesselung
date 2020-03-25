package des;

import java.util.List;

public class Textblock32Bit extends BitBlock {
    final byte[] textblock32;
    List<List<Integer>> textExpansion;
    List<List<Integer>> textPermutation;

    public Textblock32Bit(byte[] textblock) {
        checkSize(NumberOfBytes.TEXTBLOCK32BIT.length, textblock.length);
        this.textblock32 = textblock;
        initializeTextExpansion();
        initializeTextPermutation();
    }

    private void initializeTextExpansion() {
        List<Integer> byte0 = List.of(31, 1, 2, 3, 4, 5, 4, 5);
        List<Integer> byte1 = List.of(6, 7, 8, 9, 8, 9, 10, 11);
        List<Integer> byte2 = List.of(12, 13, 12, 13, 14, 15, 16, 17);
        List<Integer> byte3 = List.of(16, 17, 18, 19, 20, 21, 20, 21);
        List<Integer> byte4 = List.of(22, 23, 24, 25, 24, 25, 26, 27);
        List<Integer> byte5 = List.of(28, 29, 28, 29, 30, 31, 32, 1);
        textExpansion = List.of(byte0, byte1, byte2, byte3, byte4, byte5);
    }

    private void initializeTextPermutation() {
        List<Integer> byte0 = List.of(16, 7, 20, 21, 29, 12, 28, 17);
        List<Integer> byte1 = List.of(1, 15, 23, 26, 5, 18, 31, 10);
        List<Integer> byte2 = List.of(2, 8, 24, 14, 32, 27, 3, 9);
        List<Integer> byte3 = List.of(19, 13, 30, 6, 22, 11, 4, 25);
        textPermutation = List.of(byte0, byte1, byte2, byte3);
    }


    public Textblock48Bit expandTo48Bit() {
        byte[] expandenBytes = extractNewBytes(textblock32, NumberOfBytes.TEXTBLOCK48BIT, textExpansion);
        return new Textblock48Bit(expandenBytes);
    }

    public Textblock32Bit getPermutation() {
        byte[] permutatedText = extractNewBytes(textblock32, NumberOfBytes.TEXTBLOCK32BIT, textPermutation);
        return new Textblock32Bit(permutatedText);
    }



}
