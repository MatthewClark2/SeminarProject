package prj.clark.alchemy.data;

import org.junit.Test;

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
        AlchemyCharacter ch1 = AlchemyCharacter.of(0xD83EDD14);  // Thinking face
        AlchemyCharacter ch2 = AlchemyCharacter.of(0xD83EDD14);

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
        AlchemyCharacter ch = AlchemyCharacter.of(0xD83CDF80);
        assertEquals(ch.toString(), ch.print());
    }

    @Test
    public void allValuesTruthy() {
        for (char ch = 0; ch < 65535; ++ch) {
            AlchemyCharacter c = AlchemyCharacter.of(ch);
            assertTrue(c.toBoolean());
        }
    }
}