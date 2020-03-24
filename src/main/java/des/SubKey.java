package des;

public class SubKey extends BitBlock {
    final byte[] key;

    public SubKey(byte[] key) {
        checkSize(NumberOfBytes.SUBKEY.length,key.length);
        this.key = key;
    }

}
