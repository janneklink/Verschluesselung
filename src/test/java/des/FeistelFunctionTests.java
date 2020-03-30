package des;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Assertions;

public class FeistelFunctionTests {
    @Property
    void exclusiveOr(@ForAll @Size(8) byte[] bytes) {
        for (int i = 0; i < 8; i++) {
            Assertions.assertEquals(0, FeistelFunction.exclusiveOr(bytes, bytes, NumberOfBytes.TEXTBLOCK64BIT)[i]);
        }
    }

    @Property
    void feistelFunctionDoesNotCrash(@ForAll @Size(4) byte[] bytes, @ForAll @Size(6) byte[] keyBytes) {
        Textblock32Bit lblock = new Textblock32Bit(bytes);
        SubKey subkey = new SubKey(keyBytes);
        Textblock32Bit textblock32Bit = FeistelFunction.fFunction(lblock, subkey);
    }

    @Example
    void feistelFunctionExample() {
        byte[] bytes = new byte[4];
        Textblock32Bit lblock = new Textblock32Bit(bytes);
        byte[] keyBytes = new byte[6];
        SubKey subkey = new SubKey(keyBytes);
        Textblock32Bit textblock32Bit = FeistelFunction.fFunction(lblock, subkey);

        byte[] expected = BitBlock.ints2bytes(0b11101111, 0b10100111, 0b00101100, 0b01001101);
        Textblock32Bit expectedTextblock = new Textblock32Bit(expected).getPermutation();
        String message = String.format("Expected: %s%nActual: %s", BitBlock.toString(expectedTextblock.textblock32), BitBlock.toString(textblock32Bit.textblock32));
        Assertions.assertArrayEquals(expectedTextblock.textblock32, textblock32Bit.textblock32, message);
    }

    @Example
    void joinTwo4BitTo8Bit() {
        byte first = 0b00001101;
        byte second = 0b00001001;
        byte expected = BitBlock.int2byte(0b11011001);
        byte joined = FeistelFunction.joinTwo4BitTo8Bit(first, second);
        Assertions.assertEquals(expected, joined);
    }
}
