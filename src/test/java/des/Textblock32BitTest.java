package des;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Textblock32BitTest {
    @Test
    void testExpansion() {
        byte[] byte32 = new byte[4];
        Arrays.fill(byte32,(byte)255);
        Textblock32Bit textblock32Bit = new Textblock32Bit(byte32);
        byte[] byte48 = new byte[6];
        Arrays.fill(byte48,(byte)255);
        Assertions.assertArrayEquals(byte48,textblock32Bit.expandTo48Bit().textblock48);
    }
}
