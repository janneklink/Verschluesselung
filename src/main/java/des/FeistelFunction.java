package des;

import java.util.ArrayList;


public class FeistelFunction {

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


    public static Textblock32Bit fFunction(Textblock32Bit textblock32Bit, SubKey key) {
//        byte[] exclusiveOr = exclusiveOr(textblock32Bit.textblock32,key.key,NumberOfBytes.TEXTBLOCK32BIT);
//        return new Textblock32Bit(exclusiveOr);
        Textblock48Bit expandedTextblock = textblock32Bit.expandTo48Bit();
        Textblock48Bit combinedTextblock = new Textblock48Bit(exclusiveOr(expandedTextblock.textblock48, key.key, NumberOfBytes.TEXTBLOCK48BIT));
        Textblock32Bit substitutedTextblock = substitute(combinedTextblock);
        return substitutedTextblock.getPermutation();
    }

    private static Textblock32Bit substitute(Textblock48Bit combinedTextblock) {
        byte[] packages6Bit = combinedTextblock.splitInto6BitPackages();
        byte[] substituted4BitPackages = substituteWithSBox(packages6Bit);
        byte[] collectedTo8BitPackages = collectTo8BitPackages(substituted4BitPackages);

        return new Textblock32Bit(collectedTo8BitPackages);
    }

    private static byte[] collectTo8BitPackages(byte[] packages4Bit) {
        byte[] packages8Bit = new byte[packages4Bit.length / 2];
        for (int byteN = 0; byteN < packages4Bit.length; byteN += 2) {
            byte first = packages4Bit[byteN];
            byte second = packages4Bit[byteN + 1];
            packages8Bit[byteN / 2] = joinTwo4BitTo8Bit(first, second);
        }
        return packages8Bit;
    }

    public static byte joinTwo4BitTo8Bit(byte first, byte second) {
        int firstHalf = BitBlock.maskBits(first, BitMask.FIVETOEIGHTBIT) << 4;
        int secondHalf = BitBlock.maskBits(second, BitMask.FIVETOEIGHTBIT);
        return BitBlock.int2byte((firstHalf | secondHalf));
    }

    private static byte[] substituteWithSBox(byte[] packages6Bit) {
        byte[] substituted4Bits = new byte[packages6Bit.length];
        for (int byteN = 0; byteN < packages6Bit.length; byteN++) {
            substituted4Bits[byteN] = sBoxes.get(byteN).getSubstitution(packages6Bit[byteN]);
        }
        return substituted4Bits;
    }

    public static byte[] exclusiveOr(byte[] array1, byte[] array2, NumberOfBytes numberOfBytes) {
        byte[] combinedArrays = new byte[numberOfBytes.length];
        for (int byteN = 0; byteN < numberOfBytes.length; byteN++) {
            combinedArrays[byteN] = BitBlock.int2byte(array1[byteN] ^ array2[byteN]);
        }
        return combinedArrays;
    }


}
