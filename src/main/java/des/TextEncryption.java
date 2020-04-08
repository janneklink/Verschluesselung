package des;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextEncryption {
    public static String encryptText(String cleartext, Key key) {
        List<byte[]> cleartextBlocks = get64BitBlocks(cleartext);
        List<byte[]> encryptedTextBlocks = encryptTextBlocks(cleartextBlocks, key);
        return join64BitBlocksToString(encryptedTextBlocks);
    }

    public static String decryptText(String encrypted, Key key) {
        List<byte[]> encryptedTextBlocks = get64BitBlocks(encrypted);
        List<byte[]> decryptedTextBlocks = decryptTextBlocks(encryptedTextBlocks, key);
        return join64BitBlocksToString(decryptedTextBlocks).trim();
    }

    private static List<byte[]> decryptTextBlocks(List<byte[]> encryptedTextBlocks, Key key) {
        List<byte[]> decryptedTextBlocks = new ArrayList<>();
        for (byte[] encryptedTextBlock : encryptedTextBlocks) {
            decryptedTextBlocks.add(decryptBytes(encryptedTextBlock, key));
        }
        return decryptedTextBlocks;
    }


    public static List<byte[]> get64BitBlocks(String string) {
        byte[] stringBytes = string.getBytes();
        int numberOfBytes = Math.floorDiv(stringBytes.length - 1, 8) + 1;
        List<byte[]> textblocks64Bit = new ArrayList<>();
        for (int index = 0; index < numberOfBytes - 1; index++) {
            textblocks64Bit.add(Arrays.copyOfRange(stringBytes, index * 8, (index + 1) * 8));
        }

        byte[] last64BitBlock = new byte[8];
        int firstByteOfLastBitBlock = (numberOfBytes - 1) * 8;
        if (stringBytes.length - firstByteOfLastBitBlock >= 0)
            System.arraycopy(stringBytes, firstByteOfLastBitBlock, last64BitBlock, 0, stringBytes.length - firstByteOfLastBitBlock);
        textblocks64Bit.add(last64BitBlock);

        return textblocks64Bit;
    }

    public static List<byte[]> encryptTextBlocks(List<byte[]> cleartextBlocks, Key key) {
        List<byte[]> encryptedBytes = new ArrayList<>();
        for (byte[] cleartextBlock : cleartextBlocks) {
            encryptedBytes.add(encryptBytes(cleartextBlock, key));
        }
        return encryptedBytes;
    }

    private static byte[] decryptBytes(byte[] bytes, Key key) {
        return Encryption.decrypt(new TextBlock64Bit(bytes), key).textblock64;
    }

    private static byte[] encryptBytes(byte[] bytes, Key key) {
        return Encryption.encrypt(new TextBlock64Bit(bytes), key).textblock64;
    }

    public static String join64BitBlocksToString(List<byte[]> encryptedTextBlocks) {
        byte[] stringBytes = new byte[encryptedTextBlocks.size() * 8];
        for (int textBlockIndex = 0; textBlockIndex < encryptedTextBlocks.size(); textBlockIndex++) {
            System.arraycopy(encryptedTextBlocks.get(textBlockIndex), 0, stringBytes, textBlockIndex * 8, 8);
        }
        return new String(stringBytes);
    }


}
