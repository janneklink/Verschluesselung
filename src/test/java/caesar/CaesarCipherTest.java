package caesar;


import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import org.junit.jupiter.api.Assertions;

public class CaesarCipherTest {
    //CaesarCipher(CC)(String,0) : String
    @Property
    void testNullShift(@ForAll @AlphaChars String string ){
        boolean inputEqualsOutput = CaesarCipher.standardCaesarCipher(string,0)==string;
        Assertions.assertTrue(inputEqualsOutput);
    }
    //CC(a,1):b
    //CC(a,9):j
    //CC(a,26):a
    //CC(a,27):b
}
