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
}
