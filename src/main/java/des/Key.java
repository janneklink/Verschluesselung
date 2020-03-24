package des;

import java.util.ArrayList;
import java.util.List;

public class Key extends BitBlock {
    final byte[] key;
    static final int numberOfSubKeys = 16;

    static final List<Integer> rotations = List.of(1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1);

    public Key(byte[] key) {
        checkSize(key.length, NumberOfBytes.KEY.length);
        this.key = key;
    }

    public ArrayList<SubKey> createSubKeys() {
        ArrayList<SubKey> SubKeys = new ArrayList<>();
        LeftKey leftHalfKey = new LeftKey(key);
        RightKey rightHalfKey = new RightKey(key);
        for (int round = 0; round < numberOfSubKeys; round++) {
            int numberOfRotations = rotations.get(round);
            leftHalfKey.rotate(numberOfRotations);
            rightHalfKey.rotate(numberOfRotations);
            SubKeys.add(new SubKey(generateSubKey(leftHalfKey, rightHalfKey)));
        }

        return SubKeys;
    }

    private byte[] generateSubKey(LeftKey leftHalfKey, RightKey rightHalfKey) {
        byte[] firstHalf = leftHalfKey.getPermutatedHalfKey();
        byte[] secondHalf = rightHalfKey.getPermutatedHalfKey();
        byte[] subKey = new byte[6];
        subKey[0] = firstHalf[0];
        subKey[1] = firstHalf[1];
        subKey[2] = firstHalf[2];
        subKey[3] = secondHalf[0];
        subKey[4] = secondHalf[1];
        subKey[5] = secondHalf[2];
        return subKey;
    }


}

class HalfKey extends BitBlock {
    List<List<Integer>> keyCreationBitPermutation;
    List<List<Integer>> keyPermutation;
    byte[] halfKey;

    protected byte getByte(int bytenumber, byte[] key, List<List<Integer>> permutation) {
        byte result = 0b00000000;
        int numberOfBits = 8;
        if (bytenumber == 3) {
            numberOfBits = 4;
        }
        for (int bit = 0; bit < numberOfBits; bit++) {
            int bitnumber = permutation.get(bytenumber).get(bit);
            result = (byte) ((result << 1) | getNthBit(key, bitnumber));
        }
        return result;
    }

    public byte[] getHalfKey(byte[] key) {
        byte[] halfKey = new byte[4];
        halfKey[0] = getByte(0, key, keyCreationBitPermutation);
        halfKey[1] = getByte(1, key, keyCreationBitPermutation);
        halfKey[2] = getByte(2, key, keyCreationBitPermutation);
        halfKey[3] = getByte(3, key, keyCreationBitPermutation);
        return halfKey;
    }

    public byte[] getPermutatedHalfKey() {
        byte[] permutatedHalfKey = new byte[3];
        permutatedHalfKey[0] = getByte(0, halfKey, keyPermutation);
        System.out.println(Integer.toBinaryString(permutatedHalfKey[0]));
        permutatedHalfKey[1] = getByte(1, halfKey, keyPermutation);
        permutatedHalfKey[2] = getByte(2, halfKey, keyPermutation);
        return permutatedHalfKey;
    }


    public void rotate(int numberOfRotations) {
        for (int rotation = 0; rotation < numberOfRotations; rotation++) {
            halfKey = getRotatedHalfKey();
        }
    }

    protected byte[] getRotatedHalfKey() {
        byte[] rotatedKey = new byte[4];
        rotatedKey[0] = (byte) ((halfKey[0] << 1) | (maskBits(halfKey[1], BitMask.FIRSTBIT) >> 7));
        rotatedKey[1] = (byte) ((halfKey[1] << 1) | (maskBits(halfKey[2], BitMask.FIRSTBIT) >> 7));
        rotatedKey[2] = (byte) ((halfKey[2] << 1) | (maskBits(halfKey[3], BitMask.FIFTHBIT) >> 3));
        rotatedKey[3] = (byte) (maskBits((byte) (halfKey[3] << 1), BitMask.FIVETOEIGHTBIT) | (maskBits(halfKey[0], BitMask.FIRSTBIT) >> 7));
        return rotatedKey;
    }
}

class LeftKey extends HalfKey {

    LeftKey(byte[] key) {
        checkSize(key.length, NumberOfBytes.KEY.length);

        initializeCreationBitPermutation();
        initializeBitPermutation();
        this.halfKey = getHalfKey(key);
        System.out.println(Integer.toBinaryString(getNthBit(halfKey,3)));
    }

    private void initializeCreationBitPermutation() {
        List<Integer> byte0 = List.of(57, 49, 41, 33, 25, 17, 9, 1);
        List<Integer> byte1 = List.of(58, 50, 42, 34, 26, 18, 10, 2);
        List<Integer> byte2 = List.of(59, 51, 43, 35, 27, 19, 11, 3);
        List<Integer> byte3 = List.of(60, 52, 44, 36);
        this.keyCreationBitPermutation = List.of(byte0, byte1, byte2, byte3);
    }

    private void initializeBitPermutation() {
        List<Integer> byte0 = List.of(14, 17, 11, 24, 1, 5, 3, 32);
        List<Integer> byte1 = List.of(15, 6, 21, 10, 23, 19, 12, 4);
        List<Integer> byte2 = List.of(30, 8, 16, 7, 31, 20, 13, 2);
        this.keyPermutation = List.of(byte0, byte1, byte2);
    }

}

class RightKey extends HalfKey {

    RightKey(byte[] key) {
        checkSize(key.length, NumberOfBytes.KEY.length);

        initializeCreationBitPermutation();
        initializeBitPermutation();
        this.halfKey = getHalfKey(key);
    }

    private void initializeCreationBitPermutation() {
        List<Integer> byte0 = List.of(63, 55, 47, 39, 31, 23, 15, 7);
        List<Integer> byte1 = List.of(62, 54, 46, 38, 39, 22, 14, 6);
        List<Integer> byte2 = List.of(61, 53, 45, 37, 29, 21, 13, 5);
        List<Integer> byte3 = List.of(28, 20, 12, 4);
        this.keyCreationBitPermutation = List.of(byte0, byte1, byte2, byte3);
    }

    private void initializeBitPermutation() {
        List<Integer> byte0 = List.of(13, 24, 3, 9, 19, 31, 2, 12);
        List<Integer> byte1 = List.of(23, 17, 5, 20, 16, 21, 11, 32);
        List<Integer> byte2 = List.of(6, 29, 18, 14, 22, 8, 1, 4);
        this.keyPermutation = List.of(byte0, byte1, byte2);
    }
}
