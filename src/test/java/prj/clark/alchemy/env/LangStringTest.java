package prj.clark.alchemy.env;

import org.junit.Test;

import static org.junit.Assert.*;

// TODO(matthew-c21) - Add testing for the toString methods.
public class LangStringTest {
    @Test
    public void toStringReturnsCorrectStringValue() {
        Data s = LangString.of("asdf");
        Data t = LangString.of("lkjlkhkj");

        assertEquals("asdf", s.toString());
        assertEquals("lkjlkhkj", t.toString());
    }

    @Test
    public void equalValuesEqual() {
        Data s = LangString.of("hello");
        // While the call to new is redundant, it does allow for me to ensure that there isn't an underlying bug being
        // hidden by the caching of the string literal.
        Data t = LangString.of(new String("hello"));
        assertEquals(s, t);
    }

    @Test
    public void unequalValuesNotEqual() {
        Data s = LangString.of("hello");
        Data t = LangString.of("goodbye");
        assertNotEquals(s, t);
    }

    @Test
    public void generatedValuesOfTypeString() {
        Data s = LangString.of("hello world");
        assertEquals(PrimitiveType.STRING, s.getType());
    }
}