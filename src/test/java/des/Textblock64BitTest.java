package des;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Textblock64BitTest {
    @Property
    void initialAndFinalPermutationProperty(@ForAll @Size(value = 8) byte[] bytes) {
        TextBlock64Bit textblock = new TextBlock64Bit(bytes);
        TextBlock64Bit initialPermutation = textblock.getInitialPermutation();
        TextBlock64Bit finalPermutation = initialPermutation.getFinalPermutation();
        Assertions.assertArrayEquals(textblock.textblock64, finalPermutation.textblock64);
    }

    @Test
    void initialAndFinalPermutationTest() {
        byte[] bytes = new byte[8];
        bytes[7] = (byte)(0b00000001);
        TextBlock64Bit textblock = new TextBlock64Bit(bytes);
        TextBlock64Bit initialPermutation = textblock.getInitialPermutation();
        TextBlock64Bit finalPermutation = initialPermutation.getFinalPermutation();
        Assertions.assertArrayEquals(textblock.textblock64, finalPermutation.textblock64);

    }
}
