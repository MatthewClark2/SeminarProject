package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.*;

import java.util.Collections;

import static org.junit.Assert.*;
import static prj.clark.alchemy.data.AlchemyBoolean.FALSE;
import static prj.clark.alchemy.data.AlchemyBoolean.TRUE;

public class AndNodeTest {
    private Data and(Data a, Data b) {
        return new AndNode(new LiteralNode(a), new LiteralNode(b)).evaluate(null);
    }

    @Test
    public void booleanLiteralTruthTable() {
        assertEquals(TRUE, and(TRUE, TRUE));
        assertEquals(FALSE, and(TRUE, FALSE));
        assertEquals(FALSE, and(FALSE, TRUE));
        assertEquals(FALSE, and(FALSE, FALSE));
    }

    @Test
    public void nonBooleanLiteralTruthTable() {
        assertEquals(TRUE, and(AlchemyFloat.of(1), AlchemyInt.of(2)));
        assertEquals(FALSE, and(AlchemyString.of("asdf"), new EagerAlchemyList(Collections.emptyList())));
        assertEquals(FALSE, and(AlchemyFloat.of(0), TRUE));
        assertEquals(FALSE, and(AlchemyString.of(""), FALSE));
    }
}