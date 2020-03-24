package des;

public class BitBlock {

    protected void checkSize(int value, int expected) {
        if (value != expected) {
            throw new IllegalArgumentException();
        }
    }

    public static byte maskBits(byte input, BitMask mask) {
        return (byte) (input & mask.mask);
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
}
