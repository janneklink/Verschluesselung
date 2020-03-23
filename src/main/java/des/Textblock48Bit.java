package des;

public class Textblock48Bit extends BitBlock {
    final byte[] textblock48;

    public Textblock48Bit(byte[] textblock48) {
        checkSize(NumberOfBytes.TEXTBLOCK48BIT.length,textblock48.length);
        this.textblock48 = textblock48;
    }
}
