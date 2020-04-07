package des;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;

public class Textblock64BitTest {
    @Property
    void initialAndFinalPermutationProperty(@ForAll @Size(value = 8) byte[] bytes) {
        TextBlock64Bit textblock = new TextBlock64Bit(bytes);
        TextBlock64Bit initialPermutation = textblock.getInitialPermutation();
        TextBlock64Bit finalPermutation = initialPermutation.getFinalPermutation();
        Assertions.assertArrayEquals(textblock.textblock64, finalPermutation.textblock64);
    }

    @Example
    void initialAndFinalPermutationTest() {
        byte[] bytes = new byte[8];
        bytes[7] = (byte) (0b00000001);
        TextBlock64Bit textblock = new TextBlock64Bit(bytes);
        TextBlock64Bit initialPermutation = textblock.getInitialPermutation();
        TextBlock64Bit finalPermutation = initialPermutation.getFinalPermutation();
        Assertions.assertArrayEquals(textblock.textblock64, finalPermutation.textblock64);
    }

    @Property
    void join(
            @ForAll @Size(4) byte[] left,
            @ForAll @Size(4) byte[] right
    ) {
        Textblock32Bit lBlock = new Textblock32Bit(left);
        Textblock32Bit rBlock = new Textblock32Bit(right);
        TextBlock64Bit textBlock64Bit = TextBlock64Bit.joinTwo32Bit(lBlock, rBlock);

        byte[] expected = Arrays.copyOf(left, 8);
        System.arraycopy(right, 0, expected, 4, 4);
        Assertions.assertArrayEquals(expected, textBlock64Bit.textblock64);
    }

    @Property
    void splitInLandR(
            @ForAll @Size(4) byte[] left,
            @ForAll @Size(4) byte[] right
    ) {
        TextBlock64Bit joined = TextBlock64Bit.joinTwo32Bit(new Textblock32Bit(left), new Textblock32Bit(right));

        Textblock32Bit lblock = joined.getLBlock();
        Textblock32Bit rBlock = joined.getRBlock();

        Assertions.assertArrayEquals(left, lblock.textblock32);
        Assertions.assertArrayEquals(right, rBlock.textblock32);
    }
}
