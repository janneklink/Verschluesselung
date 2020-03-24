package des;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Key extends BitBlock {
    final byte[] key;
    static final int numberOfSubKeys = 16;

    static final ArrayList<Integer> rotations = new ArrayList<>() {
        {
            Collections.addAll(Arrays.asList(1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1));
        }
    };

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
        return new byte[0];
    }


}

class HalfKey extends BitBlock {
    ArrayList<ArrayList> bitPermutation;
    byte[] halfKey;

    protected byte getByte(int bytenumber, byte[] key) {
        byte result = 0b00000000;
        int numberOfBits = 8;
        if (bytenumber == 3) {
            numberOfBits = 4;
        }
        for (int bit = 0; bit < numberOfBits; bit++) {
            int bitnumber = (int) bitPermutation.get(bytenumber).get(bit);
            result = (byte) ((result << 1) | getNthBit(key, bitnumber));
        }
        return result;
    }

    public byte[] getHalfKey(byte[] key) {
        byte[] rightHalf = new byte[4];
        rightHalf[0] = getByte(0, key);
        rightHalf[1] = getByte(1, key);
        rightHalf[2] = getByte(2, key);
        rightHalf[3] = getByte(3, key);
        return rightHalf;
    }

    public void rotate(int numberOfRotations) {
        for (int rotation = 0; rotation < numberOfRotations; rotation++) {
            halfKey = getRotatedHalfKey();
        }
    }

    protected byte[] getRotatedHalfKey() {
        return new byte[4];
    }
}

class LeftKey extends HalfKey {

    LeftKey(byte[] key) {
        checkSize(key.length, NumberOfBytes.KEY.length);
        this.halfKey = getHalfKey(key);
        initializeBitPermutation();
    }

    private void initializeBitPermutation() {
        ArrayList<Integer> byte0 = new ArrayList<>() {
            {
                Collections.addAll(Arrays.asList(57, 49, 41, 33, 25, 17, 9, 1));
            }
        };
        ArrayList<Integer> byte1 = new ArrayList<>() {
            {
                Collections.addAll(Arrays.asList(58, 50, 42, 34, 26, 18, 10, 2));
            }
        };
        ArrayList<Integer> byte2 = new ArrayList<>() {
            {
                Collections.addAll(Arrays.asList(59, 51, 43, 35, 27, 19, 11, 3));
            }
        };
        ArrayList<Integer> byte3 = new ArrayList<>() {
            {
                Collections.addAll(Arrays.asList(60, 52, 44, 36));
            }
        };
        this.bitPermutation = new ArrayList() {
            {
                Collections.addAll(Arrays.asList(byte0, byte1, byte2, byte3));
            }
        };
    }

}

class RightKey extends HalfKey {

    RightKey(byte[] key) {
        checkSize(key.length, NumberOfBytes.KEY.length);
        this.halfKey = getHalfKey(key);
        initializeBitPermutation();
    }

    private void initializeBitPermutation() {
        ArrayList<Integer> byte0 = new ArrayList<>() {
            {
                Collections.addAll(Arrays.asList(63, 55, 47, 39, 31, 23, 15, 7));
            }
        };
        ArrayList<Integer> byte1 = new ArrayList<>() {
            {
                Collections.addAll(Arrays.asList(62, 54, 46, 38, 39, 22, 14, 6));
            }
        };
        ArrayList<Integer> byte2 = new ArrayList<>() {
            {
                Collections.addAll(Arrays.asList(61, 53, 45, 37, 29, 21, 13, 5));
            }
        };
        ArrayList<Integer> byte3 = new ArrayList<>() {
            {
                Collections.addAll(Arrays.asList(28, 20, 12, 4));
            }
        };
        this.bitPermutation = new ArrayList() {
            {
                Collections.addAll(Arrays.asList(byte0, byte1, byte2, byte3));
            }
        };
    }

}
