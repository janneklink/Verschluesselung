package des;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Assertions;

public class EncryptionDecryptionProperty {
    @Property
    void encryptionAndDecryption(@ForAll @Size(value = 8) byte[] cleartextbytes, @ForAll @Size(value = 8) byte[] keybytes) {
        TextBlock64Bit cleartext = new TextBlock64Bit(cleartextbytes);
        byte[] newKeybytes = new byte[8];
        Key key = new Key(newKeybytes);
        TextBlock64Bit encryptedText = Encryption.encrypt(cleartext, key);
        TextBlock64Bit decryptedText = Encryption.decrypt(cleartext,key);
        Assertions.assertArrayEquals(encryptedText.textblock64,decryptedText.textblock64);
    }
}
