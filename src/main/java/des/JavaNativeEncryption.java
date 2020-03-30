package des;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class JavaNativeEncryption {
    public static byte[] encrypt(TextBlock64Bit cleartext, Key key) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.key, "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] textEncrypted = cipher.doFinal(cleartext.textblock64);
        return textEncrypted;
    }

    public static TextBlock64Bit decrypt(TextBlock64Bit encryptedText, Key key) {
        return null;
    }
}
