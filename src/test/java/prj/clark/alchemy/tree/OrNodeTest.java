package prj.clark.alchemy.tree;

import org.junit.Test;
import prj.clark.alchemy.data.*;

import java.util.Collections;

import static org.junit.Assert.*;
import static prj.clark.alchemy.data.AlchemyBoolean.FALSE;
import static prj.clark.alchemy.data.AlchemyBoolean.TRUE;

public class OrNodeTest {
    private Data or(Data a, Data b) {
        return new OrNode(new LiteralNode(a), new LiteralNode(b)).evaluate(null);
    }

    @Test
    public void booleanLiteralTruthTable() {
        assertEquals(TRUE, or(TRUE, TRUE));
        assertEquals(TRUE, or(TRUE, FALSE));
        assertEquals(TRUE, or(FALSE, TRUE));
        assertEquals(FALSE, or(FALSE, FALSE));
    }

    @Test
    public void nonBooleanLiteralTruthTable() {
        assertEquals(TRUE, or(AlchemyFloat.of(1), AlchemyInt.of(2)));
        assertEquals(TRUE, or(AlchemyString.of("asdf"), new EagerAlchemyList(Collections.emptyList())));
        assertEquals(TRUE, or(AlchemyFloat.of(0), TRUE));
        assertEquals(FALSE, or(AlchemyString.of(""), FALSE));
    }
}