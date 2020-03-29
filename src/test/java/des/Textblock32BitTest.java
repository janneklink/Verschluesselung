package des;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Textblock32BitTest {
    @Test
    void expandAllOnes() {
        byte[] byte32 = new byte[4];
        Arrays.fill(byte32, BitBlock.int2byte(0b11111111));
        Textblock32Bit textblock32Bit = new Textblock32Bit(byte32);

        byte[] byte48 = new byte[6];
        Arrays.fill(byte48, BitBlock.int2byte(0b11111111));
        Assertions.assertArrayEquals(byte48, textblock32Bit.expandTo48Bit().textblock48);
    }
}
