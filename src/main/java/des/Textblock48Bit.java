package des;

public class Textblock48Bit extends BitBlock {
    final byte[] textblock48;

    public Textblock48Bit(byte[] textblock48) {
        checkSize(NumberOfBytes.TEXTBLOCK48BIT.length, textblock48.length);
        this.textblock48 = textblock48;
    }

    public byte[] splitInto6BitPackages() {
        byte[] packages6Bit = new byte[8];
        packages6Bit[0] = getPackage0();
        packages6Bit[1] = getPackage1();
        packages6Bit[2] = getPackage2();
        packages6Bit[3] = getPackage3();
        packages6Bit[4] = getPackage4();
        packages6Bit[5] = getPackage5();
        packages6Bit[6] = getPackage6();
        packages6Bit[7] = getPackage7();
        return packages6Bit;
    }

    private byte getPackage0() {
        int digits1To6 = (maskBits(textblock48[0], BitMask.FIRSTTOSIXBIT) >> 2);
        return int2byte(digits1To6);
    }

    private byte getPackage1() {
        int digits1and2 = (maskBits(textblock48[0], BitMask.SEVENANDEIGHTBIT) << 4);
        int digit3To6 = (maskBits(textblock48[1], BitMask.FIRSTTOFOURBIT) >> 4);
        return int2byte(digits1and2 | digit3To6);
    }

    private byte getPackage2() {
        int digits1To4 = (maskBits(textblock48[1], BitMask.FIVETOEIGHTBIT) << 2);
        int digits5and6 = (maskBits(textblock48[2], BitMask.FIRSTANDSECONDBIT) >> 6);
        return int2byte(digits1To4 | digits5and6);
    }

    private byte getPackage3() {
        int digits1To6 = (maskBits(textblock48[2], BitMask.THREETOEIGHTBIT));
        return int2byte(digits1To6);
    }

    private byte getPackage4() {
        int digits1To6 = (maskBits(textblock48[3], BitMask.FIRSTTOSIXBIT) >> 2);
        return int2byte(digits1To6);
    }

    private byte getPackage5() {
        int digits1and2 = (maskBits(textblock48[3], BitMask.SEVENANDEIGHTBIT) << 4);
        int digit3To6 = (maskBits(textblock48[4], BitMask.FIRSTTOFOURBIT) >> 4);
        return int2byte(digits1and2 | digit3To6);
    }

    private byte getPackage6() {
        int digits1To4 = (maskBits(textblock48[4], BitMask.FIVETOEIGHTBIT) << 2);
        int digits5and6 = (maskBits(textblock48[5], BitMask.FIRSTANDSECONDBIT) >> 6);
        return int2byte(digits1To4 | digits5and6);
    }

    private byte getPackage7() {
        int digits1To6 = (maskBits(textblock48[5], BitMask.THREETOEIGHTBIT));
        return int2byte(digits1To6);
    }
}
