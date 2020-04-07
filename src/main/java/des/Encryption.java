package des;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Encryption {
    public static TextBlock64Bit encrypt(TextBlock64Bit cleartext, Key key) {
        TextBlock64Bit initialTextPermutation = cleartext.getInitialPermutation();
        List<SubKey> subKeys = key.createSubKeys();
        Textblock32Bit lBlock = initialTextPermutation.getLBlock();
        Textblock32Bit rBlock = initialTextPermutation.getRBlock();

        return applySubkeys(subKeys, lBlock, rBlock).getFinalPermutation();
    }

    private static TextBlock64Bit applySubkeys(List<SubKey> subKeys, Textblock32Bit lBlock, Textblock32Bit rBlock) {
        Textblock32Bit newLBlock;
        Textblock32Bit newRBlock;
        for (int i = 0; i < subKeys.size(); i++) {
            SubKey subkey = subKeys.get(i);

            Textblock32Bit resultFFunction = FeistelFunction.fFunction(lBlock, subkey);
            newLBlock = new Textblock32Bit(FeistelFunction.exclusiveOr(resultFFunction.textblock32, rBlock.textblock32, NumberOfBytes.TEXTBLOCK32BIT));
            newRBlock = lBlock;

            lBlock = newLBlock;
            rBlock = newRBlock;


        }
        return TextBlock64Bit.joinTwo32Bit(rBlock, lBlock);
    }

    public static TextBlock64Bit decrypt(TextBlock64Bit encryptedText, Key key) {
        TextBlock64Bit initialTextPermutation = encryptedText.getInitialPermutation();
        List<SubKey> subKeys = key.createSubKeys();
        Textblock32Bit lBlock = initialTextPermutation.getLBlock();
        Textblock32Bit rBlock = initialTextPermutation.getRBlock();

        List<SubKey> revertedSubkeys = new ArrayList<>(subKeys);
        Collections.reverse(revertedSubkeys);

        return applySubkeys(revertedSubkeys, lBlock, rBlock).getFinalPermutation();
    }
}
