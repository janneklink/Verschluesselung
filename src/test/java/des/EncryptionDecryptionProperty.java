package des;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.StringLength;
import org.junit.jupiter.api.Assertions;

public class EncryptionDecryptionProperty {
    @Property
    void encryptText(@ForAll @AlphaChars @NumericChars @StringLength(8) String message, @ForAll @Size(value = 8) byte[] keybytes) throws Exception {
        TextBlock64Bit cleartext = new TextBlock64Bit(message.getBytes());
        Key key = new Key(keybytes);
        TextBlock64Bit encryptedText = Encryption.encrypt(cleartext, key);

        TextBlock64Bit decryptedText = Encryption.decrypt(encryptedText, key);

        String decryptedMessage = new String(decryptedText.textblock64);

        Assertions.assertEquals(message, decryptedMessage);
    }

    @Property
    void encryptionAndDecryption(@ForAll @Size(value = 8) byte[] cleartextbytes, @ForAll @Size(value = 8) byte[] keybytes) throws Exception {
        TextBlock64Bit cleartext = new TextBlock64Bit(cleartextbytes);
        Key key = new Key(keybytes);
        TextBlock64Bit encryptedText = Encryption.encrypt(cleartext, key);

        TextBlock64Bit decryptedText = Encryption.decrypt(encryptedText, key);
        Assertions.assertArrayEquals(cleartext.textblock64, decryptedText.textblock64);
    }

    @Example
    void simpleExample() throws Exception {
        byte[] cleartext = new byte[8];
        byte[] key = new byte[8];
        encryptionAndDecryption(cleartext, key);
    }

    @Example
    void anotherSimpleExample() throws Exception {
        byte[] cleartext = new byte[]{1, 2, 3, 4, 5, 6, 7, 8};
        byte[] key = new byte[]{
                0, 0, 0, 0, -128, -128, -128, -128
        };
        encryptionAndDecryption(cleartext, key);
    }
}
