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
        byte[] subKeyBytes = new byte[6];
        Arrays.fill(subKeyBytes, (byte) 255);
        SubKey testSubKey = new SubKey(subKeyBytes);
        for (int i = 0; i < 16; i++) {
            Assertions.assertArrayEquals(testSubKey.key,subKeys.get(i).key);
        }

    }
}
