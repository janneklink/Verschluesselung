package des;

import java.util.List;

public class BitBlock {

    protected void checkSize(int value, int expected) {
        if (value != expected) {
            throw new IllegalArgumentException();
        }
    }

    protected byte[] extractNewBytes(byte[] oldBytes, NumberOfBytes numberOfBytes, List<List<Integer>> permutation) {
        byte[] newBytes = new byte[numberOfBytes.length];
        for (int byteNumber = 0; byteNumber < numberOfBytes.length; byteNumber++) {
            newBytes[byteNumber] = getByte(byteNumber, oldBytes, permutation);
        }
        return newBytes;
    }

    protected byte getByte(int bytenumber, byte[] bytes, List<List<Integer>> permutation) {
        byte result = 0b00000000;
        int numberOfBits = 8;
        if (bytenumber == 3) {
            numberOfBits = 4;
        }
        for (int bit = 0; bit < numberOfBits; bit++) {
            int bitnumber = permutation.get(bytenumber).get(bit);
            result = (byte) ((result << 1) | getNthBit(bytes, bitnumber));
        }
        return result;
    }

    protected byte getNthBit(byte[] bytes, int n) {
        int bytenumber = (n - 1) / 8;
        int bitnumber = (n - 1) % 8;
        BitMask bitMask = getSingleBitMask(bitnumber);
        return (byte) (maskBits(bytes[bytenumber], bitMask) >> (7 - bitnumber));
    }

    protected BitMask getSingleBitMask(int bitnumber) {
        switch (bitnumber) {
            case 0:
                return BitMask.FIRSTBIT;
            case 1:
                return BitMask.SECONDBIT;
            case 2:
                return BitMask.THIRDBIT;
            case 3:
                return BitMask.FOURTHBIT;
            case 4:
                return BitMask.FIFTHBIT;
            case 5:
                return BitMask.SIXTHBIT;
            case 6:
                return BitMask.SEVENTHBIT;
            case 7:
                return BitMask.LASTBIT;
        }
        return BitMask.EMPTY;
    }

    public static byte maskBits(byte input, BitMask mask) {
        return (byte) (input & mask.mask);
    }
}
