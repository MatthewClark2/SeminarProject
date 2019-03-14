package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.Data;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.*;
import static prj.clark.alchemy.data.AlchemyBoolean.FALSE;
import static prj.clark.alchemy.data.AlchemyBoolean.TRUE;

public class LogicalInversionNodeTest {
    private Data invert(Valued a) {
        return new LogicalInversionNode(a).evaluate(null);
    }

    @Test
    public void truthyValueReturnsFalsey() {
        assertEquals(FALSE, invert(bool(true)));
        assertEquals(FALSE, invert(str("abc")));
        assertEquals(FALSE, invert(i64(-11)));
        assertEquals(FALSE, invert(f64(12.75)));
    }

    @Test
    public void falseyValueReturnsTruthy() {
        assertEquals(TRUE, invert(bool(false)));
        assertEquals(TRUE, invert(str("")));
        assertEquals(TRUE, invert(i64(0)));
        assertEquals(TRUE, invert(f64(0.0)));
    }
}