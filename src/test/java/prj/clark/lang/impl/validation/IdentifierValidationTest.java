package prj.clark.lang.impl.validation;

import org.junit.Test;
import prj.clark.lang.impl.err.InvalidIdentifierException;

import static org.junit.Assert.*;
import static prj.clark.lang.impl.validation.IdentifierValidation.*;

public class IdentifierValidationTest {
    @Test(expected = InvalidIdentifierException.class)
    public void nullInvalid() {
        validate(null);
    }

    @Test(expected = InvalidIdentifierException.class)
    public void numericStartInvalid() {
        validate("5alpha");
    }

    @Test(expected = InvalidIdentifierException.class)
    public void emptyStringInvalid() {
        validate("");
    }

    @Test
    public void underscoreValidStart() {
        validate("_hello");
        validate("_1");
    }

    @Test
    public void alphanumericValid() {
        validate("hello1worl_d");
    }

    @Test(expected = InvalidIdentifierException.class)
    public void specialInvalid() {
        // Special case, as $ is valid in java, but not the test language.
        validate("hellow$world");
    }

    @Test
    public void underscoreUnbound() {
        assertTrue(isUnboundIdentifier("_"));
    }

    @Test
    public void nonUnderscoresBound() {
        assertFalse(isUnboundIdentifier("___"));
        assertFalse(isUnboundIdentifier("hello"));
        assertFalse(isUnboundIdentifier("wo2lkj234"));
        assertFalse(isUnboundIdentifier("l0909__"));
        assertFalse(isUnboundIdentifier("_1_"));
    }
}