package des;

public class Key extends BitBlock {
    final byte[] key;

    public Key(byte[] key) {
        checkSize(NumberOfBytes.KEY.length,key.length);
        this.key = key;
    }

}
