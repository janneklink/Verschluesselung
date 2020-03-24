package des;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class KeyTest {
    @Test
    void keyGenerationAndPermutationTest() {
        byte[] keyBytes = new byte[8];
        Arrays.fill(keyBytes, (byte) 255);
        Key key = new Key(keyBytes);
        ArrayList<SubKey> subKeys = key.createSubKeys();
        System.out.println(Integer.toBinaryString(subKeys.get(1).key[0]));
        byte[] subKeyBytes = new byte[6];
        Arrays.fill(keyBytes, (byte) 255);
        SubKey testSubKey = new SubKey(subKeyBytes);
        Assertions.assertArrayEquals(subKeys.get(0).key, testSubKey.key);
    }
}
