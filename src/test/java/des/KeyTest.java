package des;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;


public class KeyTest {
    @Test
    void generateAndPermutateAllZerosKey() {
        byte[] keyBytes = new byte[8];
        Key key = new Key(keyBytes);
        ArrayList<SubKey> subKeys = key.createSubKeys();
        byte[] subKeyBytes = new byte[6];
        SubKey testSubKey = new SubKey(subKeyBytes);
        for (int i = 0; i < 16; i++) {
            Assertions.assertArrayEquals(testSubKey.key, subKeys.get(i).key);
        }
    }

    @Test
    void generateAndPermutateAllOnesKey() {
        byte[] keyBytes = new byte[8];
        Arrays.fill(keyBytes, (byte) -128);
        Key key = new Key(keyBytes);
        ArrayList<SubKey> subKeys = key.createSubKeys();
        byte[] subKeyBytes = new byte[6];
        Arrays.fill(subKeyBytes, (byte) -128);
        SubKey testSubKey = new SubKey(subKeyBytes);
        for (int i = 0; i < 16; i++) {
            Assertions.assertArrayEquals(testSubKey.key, subKeys.get(i).key);
        }
    }

//    @Property
//    void generateAndPermutateKey(@ForAll @IntRange(min = 0, max = 7) int byteToFlip) {
//        byte[] keyBytes = new byte[8];
//        Arrays.fill(keyBytes, (byte) -128);
//        keyBytes[byteToFlip] = BitBlock.int2byte(0b11101111);
//        Key key = new Key(keyBytes);
//        ArrayList<SubKey> subKeys = key.createSubKeys();
//        byte[] allOnes = new byte[6];
//        Arrays.fill(allOnes, (byte) -128);
//        int countFlips = 0;
//        for (int i = 0; i < 16; i++) {
//            countFlips += countFlips(allOnes, subKeys.get(i).key);
//        }
//        Assertions.assertEquals(0, countFlips);
//    }
//
//    private int countFlips(byte[] allOnes, byte[] key) {
//        int flips = 0;
//        for (int i = 0; i < allOnes.length; i++) {
//            if (allOnes[i] != key[i]) {
//                flips++;
//            }
//        }
//        return flips;
//    }

}
