package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.TestUtils;
import prj.clark.alchemy.data.AlchemyFloat;
import prj.clark.alchemy.data.AlchemyString;
import prj.clark.alchemy.data.Data;

import static org.junit.Assert.*;
import static prj.clark.alchemy.TestUtils.bool;
import static prj.clark.alchemy.TestUtils.f64;
import static prj.clark.alchemy.TestUtils.str;
import static prj.clark.alchemy.data.AlchemyBoolean.FALSE;
import static prj.clark.alchemy.data.AlchemyBoolean.TRUE;

public class ConditionalTest {
    private Data cond(Valued condition, Valued ifTrue, Valued ifFalse) {
        return new Conditional(ifTrue, ifFalse, condition).evaluate(null);
    }

    @Test
    public void truthyConditionReturnsFirstValue() {
        assertEquals(TRUE, cond(bool(true), bool(true), bool(false)));
        assertEquals(FALSE, cond(bool(true), bool(false), bool(true)));
        assertEquals(AlchemyString.of("123"), cond(str("a"), str("123"), bool(false)));
    }

    @Test
    public void falseyConditionReturnsFirstValue() {
        assertEquals(FALSE, cond(bool(false), bool(true), bool(false)));
        assertEquals(TRUE, cond(bool(false), bool(false), bool(true)));
        assertEquals(AlchemyFloat.of(1.5), cond(str(""), str("123"), f64(1.5)));
    }

    @Test
    public void truthyValueDoesNotEvaluateSecondExpression() {
        assertEquals(TRUE, cond(bool(true), bool(true), new TestUtils.FailingValuedNode()));
    }

    @Test
    public void falseyValueDoesNotEvaluateFirstExpression() {
        assertEquals(TRUE, cond(bool(false), new TestUtils.FailingValuedNode(), bool(true)));
    }
}