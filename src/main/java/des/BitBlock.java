package des;

import java.util.List;

public class BitBlock {

    public static byte[] ints2bytes(int... ints) {
        byte[] result = new byte[ints.length];
        for (int i = 0; i < ints.length; i++) {
            result[i] = int2byte(ints[i]);
        }
        return result;
    }

    protected void checkSize(int value, int expected) {
        if (value != expected) {
            throw new IllegalArgumentException();
        }
    }

    protected byte[] extractNewBytes(byte[] oldBytes, NumberOfBytes numberOfBytes, List<List<Integer>> permutation) {
        byte[] newBytes = new byte[numberOfBytes.length];
        for (int index = 0; index < numberOfBytes.length; index++) {
            if (numberOfBytes == NumberOfBytes.HALFKEY && index == NumberOfBytes.HALFKEY.length - 1) {
                newBytes[index] = getHalfByte(index, oldBytes, permutation);
            } else {
                newBytes[index] = getByte(index, oldBytes, permutation);
            }
        }
        return newBytes;
    }

    protected byte getHalfByte(int bytenumber, byte[] bytes, List<List<Integer>> permutation) {
        byte result = 0b00000000;
        int numberOfBitsInHalfByte = 4;
        for (int index = 0; index < numberOfBitsInHalfByte; index++) {
            int bitnumber = permutation.get(bytenumber).get(index);
            result = int2byte((result << 1) | getNthBit(bytes, bitnumber));
        }
        return result;
    }

    protected byte getByte(int bytenumber, byte[] bytes, List<List<Integer>> permutation) {
        int result = 0b00000000;
        int numberOfBits = 8;
        for (int index = 0; index < numberOfBits; index++) {
            int bitnumber = permutation.get(bytenumber).get(index);
            result = (result << 1);
            result = (result | getNthBit(bytes, bitnumber));
        }
        return int2byte(result);
    }

    protected static int getNthBit(byte[] bytes, int n) {
        int bytenumber = (n - 1) / 8;
        int bitnumber = (n - 1) % 8;
        BitMask bitMask = getSingleBitMask(bitnumber);
        if (maskBits(bytes[bytenumber], bitMask) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    protected static BitMask getSingleBitMask(int bitnumber) {
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

    public static int maskBits(byte input, BitMask mask) {
        return mask.and(input);
    }

    public static byte int2byte(int input) {
        return input <= 127 ? (byte) input : (byte) (127 - input);
    }

    public static String toString(byte[] bytes) {
        // TODO: Do it right
        String bytestring = "";
        for (byte aByte : bytes) {
            bytestring += Integer.toBinaryString(aByte);
        }
        return bytestring;
    }
}
