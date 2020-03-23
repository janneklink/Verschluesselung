package des;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class Function {

    public static final ArrayList<SBox> sBoxes = new ArrayList<>() {
        {
            this.add(new SBox(SBox.s1box));
            this.add(new SBox(SBox.s2box));
            this.add(new SBox(SBox.s3box));
            this.add(new SBox(SBox.s4box));
            this.add(new SBox(SBox.s5box));
            this.add(new SBox(SBox.s6box));
            this.add(new SBox(SBox.s7box));
            this.add(new SBox(SBox.s8box));
        }
    };


    public static Textblock32Bit fFunction(Textblock32Bit textblock32Bit, Key key) {
        Textblock48Bit expandedTextblock = textblock32Bit.expandTo48Bit();
        Textblock48Bit combinedTextblock = new Textblock48Bit(exclusiveOr48ByteArray(expandedTextblock.textblock48, key.key));

        return substitute(combinedTextblock);
    }

    private static Textblock32Bit substitute(Textblock48Bit combinedTextblock) {
        byte[] packages6Bit = combinedTextblock.splitInto6BitPackages();
        byte[] substituted4BitPackages = substituteWithSBox(packages6Bit);
        byte[] collectedTo8BitPackages = collectTo8BitPackages(substituted4BitPackages);

        return new Textblock32Bit(collectedTo8BitPackages);
    }

    private static byte[] collectTo8BitPackages(byte[] packages4Bit) {
        byte[] packages8Bit = new byte[packages4Bit.length / 2];
        for (int byteN = 0; byteN < packages4Bit.length / 2; byteN++) {
            byte first = packages4Bit[byteN * 2];
            byte second = packages4Bit[byteN * 2 + 1];
            packages8Bit[byteN] = joinTwo4BitTo8Bit(first, second);
        }
        return packages8Bit;
    }

    private static byte joinTwo4BitTo8Bit(byte first, byte second) {
        first = (byte) (BitBlock.maskBits(first, BitMask.FIVETOEIGHTBIT) << 4);
        second = BitBlock.maskBits(second, BitMask.FIVETOEIGHTBIT);
        return (byte) (first | second);
    }

    private static byte[] substituteWithSBox(byte[] packages6Bit) {
        byte[] substituted4Bits = new byte[packages6Bit.length];
        for (int byteN = 0; byteN < packages6Bit.length; byteN++) {
            substituted4Bits[byteN] = sBoxes.get(byteN).getSubstitution(packages6Bit[byteN]);
        }
        return substituted4Bits;
    }

    public static byte[] exclusiveOr48ByteArray(byte[] array1, byte[] array2) {
        byte[] combinedArrays = new byte[NumberOfBytes.TEXTBLOCK48BIT.length];
        for (int byteN = 0; byteN < NumberOfBytes.TEXTBLOCK48BIT.length; byteN++) {
            combinedArrays[byteN] = (byte) (array1[byteN] ^ array2[byteN]);
        }
        return combinedArrays;
    }

}
