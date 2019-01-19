package prj.clark.alchemy.data;

import org.junit.Test;

import static org.junit.Assert.*;

// TODO(matthew-c21) - Add testing for the toString methods.
public class AlchemyStringTest {
    @Test
    public void toStringReturnsCorrectStringValue() {
        Data s = AlchemyString.of("asdf");
        Data t = AlchemyString.of("lkjlkhkj");

        assertEquals("asdf", s.toString());
        assertEquals("lkjlkhkj", t.toString());
    }

    @Test
    public void equalValuesEqual() {
        Data s = AlchemyString.of("hello");
        // While the call to new is redundant, it does allow for me to ensure that there isn't an underlying bug being
        // hidden by the caching of the string literal.
        Data t = AlchemyString.of(new String("hello"));
        assertEquals(s, t);
    }

    @Test
    public void unequalValuesNotEqual() {
        Data s = AlchemyString.of("hello");
        Data t = AlchemyString.of("goodbye");
        assertNotEquals(s, t);
    }

    @Test
    public void generatedValuesOfTypeString() {
        Data s = AlchemyString.of("hello world");
        assertEquals(PrimitiveType.STRING, s.getType());
    }
}