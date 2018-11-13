package prj.clark.lang.impl.tree;

import org.junit.Before;
import org.junit.Test;
import prj.clark.lang.impl.env.*;
import prj.clark.lang.impl.err.LangException;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Unit testing is done primarily with literals for the sake of simplicity.
 */
public class AbstractSyntaxTreeTest {
    private static AbstractSyntaxTree create(Data... datum) {
        return new AbstractSyntaxTree(Arrays.stream(datum).map(LiteralNode::new).collect(Collectors.toList()));
    }

    private class PrintNode implements Node {
        private StringBuilder sb;

        private PrintNode(StringBuilder sb) {
            this.sb = sb;
        }

        @Override
        public Data evaluate(Context ctx) throws LangException {
            sb.append(".");
            return null;
        }
    }

    @Test
    public void emptyTreeReturnsEmpty() {
        // This is only the case until an empty value is added to the language.
        fail("Empty value unavailable.");
    }

    @Test
    public void singleNodeReturnsCorrectly() throws LangException {
        AbstractSyntaxTree ast = new AbstractSyntaxTree(Collections.singletonList(new LiteralNode(LangBool.of(false))));
        assertEquals(LangBool.of(false), ast.execute(new DummyContext()));
    }

    @Test
    public void multiNodeListReturnsCorrectly() throws LangException {
        AbstractSyntaxTree ast = create(LangBool.of(true), LangBool.of(false), LangString.of("hello"));
        assertEquals(LangString.of("hello"), ast.execute(new DummyContext()));
    }

    @Test
    public void allStatementsExecuted() throws LangException {
        StringBuilder sb = new StringBuilder();

        AbstractSyntaxTree ast = new AbstractSyntaxTree(
                Arrays.asList(
                        new PrintNode(sb),
                        new PrintNode(sb),
                        new PrintNode(sb),
                        new PrintNode(sb),
                        new PrintNode(sb)
                )
        );

        ast.execute(new DummyContext());
        assertEquals(".....", sb.toString());
    }
}