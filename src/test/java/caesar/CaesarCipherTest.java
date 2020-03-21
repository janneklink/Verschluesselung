package caesar;


import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class CaesarCipherTest {
    //CaesarCipher(CC)(String,0) : String
    @Property
    void nullShiftProperty(@ForAll @AlphaChars String string ){
        boolean inputEqualsOutput = CaesarCipher.caesarCipher(string,0)==string;
        Assertions.assertTrue(inputEqualsOutput);
    }
    //CC(a,1):b
    @Test
    void testOneShift(){
        Assertions.assertEquals(CaesarCipher.caesarCipher("a",1),"b");
    }
    //CC(a,9):j
    @Test
    void testNineShift() {
        Assertions.assertEquals(CaesarCipher.caesarCipher("a",9),"j");
    }
    //CC(a,26):a
    @Test
    void testFullShift() {
        Assertions.assertEquals(CaesarCipher.caesarCipher("a",26),"a");
    }

    //CC(a,27):b
    @Test
    void testMultipleShift() {
        Assertions.assertEquals(CaesarCipher.caesarCipher("a",27),"b");
    }

    //CC(abc,1):bcd
    @Test
    void testMultipleCharachterShift() {
        Assertions.assertEquals(CaesarCipher.caesarCipher("abc",1),"bcd");
    }

    //CC(faab,3):idde
    @Test
    void testMultipleNonSequentialCharactersShift() {
        Assertions.assertEquals(CaesarCipher.caesarCipher("faab",3),"idde");
    }

    //CC(z,27):a
    @Test
    void testEdge() {
        Assertions.assertEquals(CaesarCipher.caesarCipher("z",27),"a");
    }

}
