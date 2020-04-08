package des;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.NotEmpty;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class TextEncryptionProperties {
    @Property
    void disAndReAssemble(@ForAll @AlphaChars @NumericChars @NotEmpty String string) {
        List<byte[]> disassembledString = TextEncryption.get64BitBlocks(string);
        String reassembledString = TextEncryption.join64BitBlocksToString(disassembledString);
        Assertions.assertEquals(string,reassembledString.trim());
    }

    @Property
    void textEnAndDeCryption(@ForAll @AlphaChars @NumericChars @NotEmpty String cleartext,@ForAll @Size(value = 8) byte[] keybytes){
        Key key = new Key(keybytes);
        String encrypted = TextEncryption.encryptText(cleartext, key);
        String decrypted = TextEncryption.decryptText(encrypted, key);
        Assertions.assertEquals(cleartext,decrypted);
    }
}
