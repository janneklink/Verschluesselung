package des;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class KeyTest {
    @Test
    void keyGenerationAndPermutationTest() {
        byte[] keyBytes = new byte[8];
        Arrays.fill(keyBytes,(byte)255);
        Key key = new Key(keyBytes);
        ArrayList<SubKey> subKeys = key.createSubKeys();

    }
}
