package des;

import java.util.Arrays;

public class TextBlock64Bit extends BitBlock {
    final byte[] textblock64;

    public TextBlock64Bit(byte[] cleartext) {
        checkSize(NumberOfBytes.TEXTBLOCK64BIT.length, cleartext.length);
        this.textblock64 = cleartext;
    }

    public Textblock32Bit getLBlock() {
        byte[] lBlock = Arrays.copyOfRange(textblock64, 0, NumberOfBytes.TEXTBLOCK32BIT.length);
        return new Textblock32Bit(lBlock);
    }

    public Textblock32Bit getRBlock() {
        byte[] rBlock = Arrays.copyOfRange(textblock64, NumberOfBytes.TEXTBLOCK32BIT.length, 2 * NumberOfBytes.TEXTBLOCK32BIT.length);
        return new Textblock32Bit(rBlock);
    }

}
