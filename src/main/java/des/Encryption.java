package des;

import java.util.ArrayList;

public class Encryption {
    public static TextBlock64Bit encrypt(TextBlock64Bit cleartext, Key key) {
        TextBlock64Bit initialTextPermutation = cleartext.getInitialPermutation();
        ArrayList<SubKey> subKeys = key.createSubKeys();
        Textblock32Bit lBlock = initialTextPermutation.getLBlock();
        Textblock32Bit rBlock = initialTextPermutation.getRBlock();
        Textblock32Bit newLBlock = lBlock;
        Textblock32Bit newRBlock = rBlock;
        for (SubKey subkey : subKeys) {
            lBlock = newLBlock;
            rBlock = newRBlock;
            Textblock32Bit resultFFunction = Function.fFunction(lBlock, subkey);
            newLBlock = new Textblock32Bit(Function.exclusiveOr(resultFFunction.textblock32, rBlock.textblock32, NumberOfBytes.TEXTBLOCK32BIT));
            newRBlock = lBlock;
        }
        TextBlock64Bit encryptedText = TextBlock64Bit.joinTwo32Bit(lBlock, rBlock).getFinalPermutation();
        return encryptedText;
    }

    public static TextBlock64Bit decrypt(TextBlock64Bit encryptedText, Key key) {
        TextBlock64Bit initialTextPermutation = encryptedText.getInitialPermutation();
        ArrayList<SubKey> subKeys = key.createSubKeys();
        Textblock32Bit lBlock = initialTextPermutation.getLBlock();
        Textblock32Bit rBlock = initialTextPermutation.getRBlock();
        for (int index = subKeys.size() - 1; index >= 0; index--) {
            SubKey subKey = subKeys.get(index);
            Textblock32Bit resultFFunction = Function.fFunction(lBlock, subKey);
            Textblock32Bit newLBlock = new Textblock32Bit(Function.exclusiveOr(resultFFunction.textblock32, rBlock.textblock32, NumberOfBytes.TEXTBLOCK32BIT));
            Textblock32Bit newRBlock = lBlock;
            lBlock = newLBlock;
            rBlock = newRBlock;
        }
        TextBlock64Bit decryptedText = TextBlock64Bit.joinTwo32Bit(lBlock, rBlock).getFinalPermutation();
        return decryptedText;
    }
}
