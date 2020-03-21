package caesar;

public class CaesarCipher {

    public static String caesarCipher(String cleartext, int caesarShift) {
        if (caesarShift == 0) {
            return cleartext;
        } else {
            String encryptedtext = "";
            for (char c : cleartext.toCharArray()) {
                encryptedtext += shiftChar(c, caesarShift);
            }
            return encryptedtext;
        }
    }

    private static char shiftChar(char c, int shift) {
        return keepCharachterInLetterRange(c + shift);
    }

    //Because we only want normal letters, it has to be checked if value is actually a letter as a char
    private static char keepCharachterInLetterRange(int letter) {
        if (letter > charValues.Z.value) {
            letter = (char) (letter - (charValues.Z.value + 1) + charValues.A.value);
            if (letter >= charValues.Z.value) {
                letter = keepCharachterInLetterRange(letter);
            }
        }
        return (char) letter;
    }


}

enum charValues {
    A(97), Z(122);

    int value;

    charValues(int value) {
        this.value = value;
    }
}
