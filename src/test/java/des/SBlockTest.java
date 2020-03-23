package des;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SBlockTest {
    @Test
    void testMiddleOutput(){
        byte input = 0b01001100;
        byte output = SBlock.getMiddleOfSix(input);
        byte expectedOutput = 0b00000110;
        Assertions.assertEquals(output,expectedOutput);
    }

    @Test
    void testBoundsOutput(){
        byte input = 0b00100001;
        byte output = SBlock.getBoundsOfSix(input);
        byte expectedOutput = 0b00000011;
        Assertions.assertEquals(output,expectedOutput);
    }
}
