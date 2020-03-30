package des;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Assertions;

public class EncryptionDecryptionProperty {
    @Property
    void encryptionAndDecryption(@ForAll @Size(value = 8) byte[] cleartextbytes, @ForAll @Size(value = 8) byte[] keybytes) throws Exception {
        @Size(value = 8) byte[] keyClone = keybytes.clone();
        TextBlock64Bit cleartext = new TextBlock64Bit(cleartextbytes);
        Key key = new Key(keybytes);
        TextBlock64Bit encryptedText = Encryption.encrypt(cleartext, key);
        TextBlock64Bit decryptedText = Encryption.decrypt(encryptedText, key);
        Assertions.assertArrayEquals(cleartext.textblock64, decryptedText.textblock64);
    }

    @Example
    void failingExample() throws Exception {
        byte[] cleartext = new byte[8];
        byte[] key = new byte[8];
        encryptionAndDecryption(cleartext, key);
    }
}
