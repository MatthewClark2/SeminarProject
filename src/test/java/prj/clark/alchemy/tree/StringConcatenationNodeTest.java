package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.DefaultContext;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.bool;
import static prj.clark.alchemy.TestUtils.i64;
import static prj.clark.alchemy.TestUtils.str;

public class StringConcatenationNodeTest {
    private static AlchemyString strcat(Data left, Valued right) {
        return strcat(new LiteralNode(left), right);
    }

    private static AlchemyString strcat(Valued left, Data right) {
        return strcat(left, new LiteralNode(right));
    }

    private static AlchemyString strcat(Valued left, Valued right) {
        Data d = new StringConcatenationNode(left, right).evaluate(new DefaultContext());
        assertTrue(d instanceof AlchemyString);
        return (AlchemyString) d;
    }

    @Test
    public void concatsTwoStrings() {
        assertEquals(AlchemyString.of("Hello, World!"), strcat(str("Hello, "), str("World!")));
    }

    @Test(expected = TypeMismatchException.class)
    public void leftOperandMayNotBeList() {
        strcat(new EagerAlchemyList(Collections.emptyList()), str(""));
    }

    @Test(expected = TypeMismatchException.class)
    public void rightOperandMayNotBeList() {
        strcat(str("foo"), new EagerAlchemyList(Collections.emptyList()));
    }

    @Test(expected = TypeMismatchException.class)
    public void leftOperandMayNotBeNumeric() {
        strcat(i64(5), str("bar"));
    }

    @Test(expected = TypeMismatchException.class)
    public void rightOperandMayNotBeNumeric() {
        strcat(str("name"), i64(100));
    }

    @Test(expected = TypeMismatchException.class)
    public void leftOperandMayNotBeTuple() {
        strcat(new AlchemyTuple(Arrays.asList(AlchemyString.of("1"), AlchemyFloat.of(-11.5))), str("quux"));
    }

    @Test(expected = TypeMismatchException.class)
    public void rightOperandMayNotBeTuple() {
        strcat(str("quux"), new AlchemyTuple(Arrays.asList(AlchemyString.of("1"), AlchemyFloat.of(-11.5))));
    }

    @Test(expected = TypeMismatchException.class)
    public void leftOperandMayNotBeBoolean() {
        strcat(bool(true), str("false"));
    }

    @Test(expected = TypeMismatchException.class)
    public void rightOperandMayNotBeBoolean() {
        strcat(str("nil"), bool(true));
    }
}