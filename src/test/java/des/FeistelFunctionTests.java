package des;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Size;
import org.junit.jupiter.api.Assertions;

public class FeistelFunctionTests {
    @Property
    void exclusiveOrTest(@ForAll @Size(value = 8) byte[] bytes) {
        for (int i = 0; i < 8; i++) {
            Assertions.assertEquals(0, FeistelFunction.exclusiveOr(bytes, bytes, NumberOfBytes.TEXTBLOCK64BIT)[3]);
        }

    }
}
