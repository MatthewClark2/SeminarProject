package prj.clark.alchemy.data;

import org.junit.Test;
import prj.clark.alchemy.err.StringFormatException;

import static org.junit.Assert.*;

public class AlchemyCharacterTest {
    @Test
    public void toStringBehavesCorrectly() {
        AlchemyCharacter ch = AlchemyCharacter.of('a');
        assertEquals("a", ch.toString());
    }

    @Test
    public void equalValuesEqual() {
        // AlchemyCharacters cannot be produced with strings, so we have to use hex integers to use unicode.
        AlchemyCharacter ch1 = AlchemyCharacter.of(0x1F914);  // Thinking face
        AlchemyCharacter ch2 = AlchemyCharacter.of(0x1F914);

        assertEquals(ch1, ch2);
    }

    @Test
    public void unequalValuesNotEqual() {
        AlchemyCharacter ch1 = AlchemyCharacter.of('a');
        AlchemyCharacter ch2 = AlchemyCharacter.of('A');

        assertNotEquals(ch1, ch2);
    }

    @Test
    public void equalValuesShareHash() {
        AlchemyCharacter ch1 = AlchemyCharacter.of('h');
        AlchemyCharacter ch2 = AlchemyCharacter.of('h');

        assertEquals(ch1.hashCode(), ch2.hashCode());
    }

    @Test
    public void unequalValuesDoNotShareHash() {
        AlchemyCharacter ch1 = AlchemyCharacter.of('h');
        AlchemyCharacter ch2 = AlchemyCharacter.of('i');

        assertNotEquals(ch1.hashCode(), ch2.hashCode());
    }

    @Test
    public void printAndToStringEquivalent() {
        AlchemyCharacter ch = AlchemyCharacter.of(0x1F380);
        assertEquals(ch.toString(), ch.print());
    }

    @Test
    public void allValuesTruthy() {
        for (char ch = 0; ch < 65535; ++ch) {
            AlchemyCharacter c = AlchemyCharacter.of(ch);
            assertTrue(c.toBoolean());
        }
    }

    @Test
    public void characterFromStringOfLengthOne() {
        Data c = AlchemyCharacter.of("q");
        assertEquals("q", c.toString());
    }

    @Test
    public void characterFromStringOfEscapedLengthOne() {
        Data c = AlchemyCharacter.of("\\n");
        assertEquals("\n", c.toString());
    }

    @Test(expected = StringFormatException.class)
    public void cannotMakeCharacterFromExcessivelyLongString() {
        AlchemyCharacter.of("ab");
    }

    @Test(expected = StringFormatException.class)
    public void cannotMakeCharacterFromExcessivelyLongStringAfterEscapes() {
        AlchemyCharacter.of("\\t\\r");
    }

    @Test
    public void standardEscapesBehaveCorrectly() {
        String[] escapes = new String[]{"\\t", "\\n", "\\\\", "\\r", "\\f", "\\\"", "\\'"};
        String[] expected = new String[]{"\t", "\n", "\\", "\r", "\f", "\"", "'"};

        for (int i = 0; i < expected.length; ++i) {
            assertEquals(expected[i], AlchemyCharacter.of(escapes[i]).print());
        }
    }

    @Test
    public void unicodeEscapedCorrectly() {
        Data s = AlchemyCharacter.of("\\u263a");
        // I would just paste the smiling emoji, but the editor I'm using keeps changing it to a different, wider format
        // when pasting.
        assertEquals("\u263a", s.toString());

        Data s2 = AlchemyCharacter.of("\\u26D4️");
        assertEquals("♍", s2.toString());
    }

    @Test(expected = StringFormatException.class)
    public void incompleteUnicodeEscapeThrowsException() {
        Data s = AlchemyCharacter.of("\\u1fd");
    }

    @Test(expected = StringFormatException.class)
    public void nonHexUnicodeEscapeThrowsException() {
        Data s = AlchemyCharacter.of("\\uabcp");
    }

    @Test(expected = StringFormatException.class)
    public void invalidEscapesFail() {
        AlchemyCharacter.of("\\k");
    }

    @Test(expected = StringFormatException.class)
    public void incompleteEscapesFail() {
        AlchemyCharacter.of("\\");
    }

}