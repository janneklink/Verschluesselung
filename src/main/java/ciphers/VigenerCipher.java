package ciphers;

public class VigenerCipher {
    public static String vigenereCipher(String cleartext, String key){
        char[] textArray = cleartext.toCharArray();
        char[] keyArray = key.toCharArray();
        for (int keyPosition = 0; keyPosition <keyArray.length ; keyPosition++) {
            for (int textPosition = keyPosition; textPosition < textArray.length; textPosition+=keyArray.length) {
                char currentCharacter = textArray[textPosition];
                textArray[textPosition] =  CaesarCipher.shiftChar(currentCharacter,keyArray[keyPosition]-charValues.A.value);
            }
        }
        return String.valueOf(textArray);
    }

    public static String decodeVigenere(String text,String key){
        return vigenereCipher(text,invertKey(key));
    }

    private static String invertKey(String key) {
        String invertedKey = "";
        for (char keyChar : key.toCharArray()) {
            invertedKey+=CaesarCipher.shiftChar(keyChar,keyChar-charValues.A.value-1);
        }
        return invertedKey;
    }
}
