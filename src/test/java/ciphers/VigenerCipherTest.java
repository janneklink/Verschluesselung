package ciphers;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.CharRange;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VigenerCipherTest {
    //VC(a,a):0
    @Test
    void aatest(){
        Assertions.assertEquals(VigenerCipher.vigenereCipher("a","a"),"a");
    }

    //VC(ab,ab):aa
    @Test
    void abtest(){
        Assertions.assertEquals(VigenerCipher.vigenereCipher("ab","ab"),"ac");
    }

    @Property
    void OneLetterKeyTest(@ForAll @AlphaChars String string, @ForAll @CharRange(from = 'a',to ='z')char key) {
        String vigenere = VigenerCipher.vigenereCipher(string,String.valueOf(key));
        String caesar = CaesarCipher.caesarCipher(string,(int)key-charValues.A.value);
        Assertions.assertEquals(vigenere,caesar);
    }

    @Property
    void decodingTest(@ForAll @AlphaChars String string, @ForAll @AlphaChars String key){
        String encodedText = VigenerCipher.vigenereCipher(string,key);
        String decodedText = VigenerCipher.decodeVigenere(encodedText,key);
        Assertions.assertEquals(string,decodedText);
    }
}
