package des;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BitBlockTest {
    @Test
    void getNthBitTest() {
        byte[] bytes = new byte[8];
        bytes[3] = (byte) (0b10000000);
        byte result = BitBlock.getNthBit(bytes, 25);
        Assertions.assertEquals(1, result);
    }
}
