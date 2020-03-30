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
        LeftKey leftHalfKey = new LeftKey(key.clone());
        RightKey rightHalfKey = new RightKey(key.clone());
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

abstract class HalfKey extends BitBlock {
    List<List<Integer>> keyCreationBitPermutation;
    List<List<Integer>> keyPermutation;
    List<List<Integer>> keyRotation;
    byte[] halfKey;

    public HalfKey(byte[] key) {
        checkSize(key.length, NumberOfBytes.KEY.length);

        initializeCreationBitPermutation();
        initializeBitPermutation();
        initializeRotation();
        this.halfKey = getHalfKey(key);
    }

    protected abstract void initializeBitPermutation();

    protected abstract void initializeCreationBitPermutation();

    public byte[] getHalfKey(byte[] key) {
        byte[] halfKeyBytes = extractNewBytes(key, NumberOfBytes.HALFKEY, keyCreationBitPermutation);
        return halfKeyBytes;
    }

    public byte[] getPermutatedHalfKey() {
        byte[] permutatedHalfKey = extractNewBytes(halfKey, NumberOfBytes.PERMUTATEDHALFKEY, keyPermutation);
        return permutatedHalfKey;
    }


    public void rotate(int numberOfRotations) {
        for (int rotation = 0; rotation < numberOfRotations; rotation++) {
            halfKey = getRotatedHalfKey();
        }
    }

    protected byte[] getRotatedHalfKey() {
        byte[] rotatedHalfKey = extractNewBytes(halfKey, NumberOfBytes.HALFKEY, keyRotation);
        return rotatedHalfKey;
    }

    @Override
    public String toString() {
        return toString(halfKey);
    }

    public void initializeRotation() {
        List<Integer> byte0 = List.of(2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> byte1 = List.of(10, 11, 12, 13, 14, 15, 16, 17);
        List<Integer> byte2 = List.of(18, 19, 20, 21, 22, 23, 24, 29);
        List<Integer> byte3 = List.of(30, 31, 32, 1);
        this.keyRotation = List.of(byte0, byte1, byte2, byte3);
    }
}

class LeftKey extends HalfKey {

    LeftKey(byte[] key) {
        super(key);
    }

    @Override
    protected void initializeCreationBitPermutation() {
        List<Integer> byte0 = List.of(57, 49, 41, 33, 25, 17, 9, 1);
        List<Integer> byte1 = List.of(58, 50, 42, 34, 26, 18, 10, 2);
        List<Integer> byte2 = List.of(59, 51, 43, 35, 27, 19, 11, 3);
        List<Integer> byte3 = List.of(60, 52, 44, 36);
        this.keyCreationBitPermutation = List.of(byte0, byte1, byte2, byte3);
    }

    @Override
    protected void initializeBitPermutation() {
        List<Integer> byte0 = List.of(14, 17, 11, 24, 1, 5, 3, 32);
        List<Integer> byte1 = List.of(15, 6, 21, 10, 23, 19, 12, 4);
        List<Integer> byte2 = List.of(30, 8, 16, 7, 31, 20, 13, 2);
        this.keyPermutation = List.of(byte0, byte1, byte2);
    }

}

class RightKey extends HalfKey {

    RightKey(byte[] key) {
        super(key);
    }

    @Override
    protected void initializeCreationBitPermutation() {
        List<Integer> byte0 = List.of(63, 55, 47, 39, 31, 23, 15, 7);
        List<Integer> byte1 = List.of(62, 54, 46, 38, 39, 22, 14, 6);
        List<Integer> byte2 = List.of(61, 53, 45, 37, 29, 21, 13, 5);
        List<Integer> byte3 = List.of(28, 20, 12, 4);
        this.keyCreationBitPermutation = List.of(byte0, byte1, byte2, byte3);
    }

    @Override
    protected void initializeBitPermutation() {
        List<Integer> byte0 = List.of(13, 24, 3, 9, 19, 31, 2, 12);
        List<Integer> byte1 = List.of(23, 17, 5, 20, 16, 21, 11, 32);
        List<Integer> byte2 = List.of(6, 29, 18, 14, 22, 8, 1, 4);
        this.keyPermutation = List.of(byte0, byte1, byte2);
    }
}
