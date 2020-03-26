package des;

import java.util.Arrays;

public class SubKey extends BitBlock {
    final byte[] key;

    public SubKey(byte[] key) {
        checkSize(NumberOfBytes.SUBKEY.length,key.length);
        this.key = key;
    }

    @Override
    public String toString() {
        return toString(key);
    }
}
