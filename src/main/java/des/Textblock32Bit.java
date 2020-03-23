package des;

public class Textblock32Bit extends BitBlock {
    final byte[] textblock32;

    public Textblock32Bit(byte[] textblock) {
        checkSize(NumberOfBytes.TEXTBLOCK32BIT.length, textblock.length);
        this.textblock32 = textblock;
    }

    /*
     * In the context of expanding the 32Bit to 48Bit according to the table on DES on wikpedia,
     * facing complexity issues
     * I decided for hard-coding each byte of the 48Bit
     * to achieve managable complexity, while coding
     * accepting less comprehensebility
     */

    public Textblock48Bit expandTo48Bit() {
        byte[] fortyEightBit = new byte[NumberOfBytes.TEXTBLOCK48BIT.length];
        fortyEightBit[0] = getByte0();
        fortyEightBit[1] = getByte1();
        fortyEightBit[2] = getByte2();
        fortyEightBit[3] = getByte3();
        fortyEightBit[4] = getByte4();
        fortyEightBit[5] = getByte5();
        return new Textblock48Bit(fortyEightBit);
    }


    private byte getByte0() {
        byte digit1 = (byte) (maskBits(textblock32[3], BitMask.LASTBIT) << 7);
        byte digit2To6 = (byte) (maskBits(textblock32[0], BitMask.FIRSTTOFIVEBIT) >> 1);
        byte digit7and8 = (byte) (maskBits(textblock32[0], BitMask.FOURANDFIVEBIT) >> 3);
        return (byte) (digit1 | digit2To6 | digit7and8);
    }

    private byte getByte1() {
        byte digit1To3 = (byte) (maskBits(textblock32[0], BitMask.LASTTHREEBIT) << 5);
        byte digit4 = (byte) (maskBits(textblock32[1], BitMask.FIRSTBIT) >> 3);
        byte digit5 = (byte) (maskBits(textblock32[0], BitMask.LASTBIT) << 3);
        byte digit6To8 = (byte) (maskBits(textblock32[1], BitMask.FIRSTTOTHREEBIT) >> 5);
        return (byte) (digit1To3 | digit4 | digit5 | digit6To8);
    }

    private byte getByte2() {
        byte digit1and2 = (byte) (maskBits(textblock32[1], BitMask.FOURANDFIVEBIT) << 3);
        byte digit3To7 = (byte) (maskBits(textblock32[1], BitMask.FOURTOEIGHTBIT) << 1);
        byte digit8 = (byte) (maskBits(textblock32[2], BitMask.FIRSTBIT) >> 7);
        return (byte) (digit1and2 | digit3To7 | digit8);
    }

    private byte getByte3() {
        byte digit1 = (byte) (maskBits(textblock32[1], BitMask.LASTBIT) << 7);
        byte digit2To6 = (byte) (maskBits(textblock32[2], BitMask.FIRSTTOFIVEBIT) >> 1);
        byte digit7and8 = (byte) (maskBits(textblock32[2], BitMask.FOURANDFIVEBIT) >> 3);
        return (byte) (digit1 | digit2To6 | digit7and8);
    }

    private byte getByte4() {
        byte digit1To3 = (byte) (maskBits(textblock32[2], BitMask.LASTTHREEBIT) << 5);
        byte digit4 = (byte) (maskBits(textblock32[3], BitMask.FIRSTBIT) >> 3);
        byte digit5 = (byte) (maskBits(textblock32[2], BitMask.LASTBIT) << 3);
        byte digit6To8 = (byte) (maskBits(textblock32[3], BitMask.FIRSTTOTHREEBIT) >> 5);
        return (byte) (digit1To3 | digit4 | digit5 | digit6To8);
    }

    private byte getByte5() {
        byte digit1and2 = (byte) (maskBits(textblock32[3], BitMask.FOURANDFIVEBIT) << 3);
        byte digit3To7 = (byte) (maskBits(textblock32[3], BitMask.FOURTOEIGHTBIT) << 1);
        byte digit8 = (byte) (maskBits(textblock32[0], BitMask.FIRSTBIT) >> 7);
        return (byte) (digit1and2 | digit3To7 | digit8);
    }


}
