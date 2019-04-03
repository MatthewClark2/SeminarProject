package prj.clark.alchemy.tree;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.AlchemyContext;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.TypeMismatchException;

import java.util.Arrays;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.*;

public class UnaryNegationNodeTest {
    private Context ctx;

    private Data negate(Valued v) {
        return new UnaryNegationNode(v).evaluate(ctx);
    }

    private Data negate(Data d) {
        return negate(new LiteralNode(d));
    }

    @Before
    public void setUp() {
        ctx = new AlchemyContext();
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotNegateString() {
        negate(str("100"));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotNegateList() {
        negate(new AlchemyRange.AlchemyRangeBuilder().build());
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotNegateTuple() {
        negate(new AlchemyTuple(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2))));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotNegateBoolean() {
        negate(bool(true));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotNegateCharacter() {
        negate(AlchemyCharacter.of('h'));
    }

    @Test(expected = TypeMismatchException.class)
    public void cannotNegateFunction() {
        negate(ctx.search("print").get());
    }

    @Test
    public void integerNegation() {
        assertEquals(AlchemyInt.of(-50), negate(i64(50)));
        assertEquals(AlchemyInt.of(110), negate(i64(-110)));
    }

    @Test
    public void floatNegation() {
        assertEquals(AlchemyFloat.of(-43.5), negate(f64(43.5)));
        assertEquals(AlchemyFloat.of(11.25), negate(f64(-11.25)));
    }
}