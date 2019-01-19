package prj.clark.alchemy.tree;

import org.junit.Assert;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.env.Context;
import prj.clark.alchemy.err.LangException;

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
    public void emptyTreeReturnsEmpty() throws LangException {
        // This is only the case until an empty value is added to the language.
        Assert.assertEquals(Empty.get(), create().execute(new DummyContext()));
    }

    @Test
    public void singleNodeReturnsCorrectly() throws LangException {
        AbstractSyntaxTree ast = new AbstractSyntaxTree(Collections.singletonList(new LiteralNode(AlchemyBoolean.of(false))));
        Assert.assertEquals(AlchemyBoolean.of(false), ast.execute(new DummyContext()));
    }

    @Test
    public void multiNodeListReturnsCorrectly() throws LangException {
        AbstractSyntaxTree ast = create(AlchemyBoolean.of(true), AlchemyBoolean.of(false), AlchemyString.of("hello"));
        Assert.assertEquals(AlchemyString.of("hello"), ast.execute(new DummyContext()));
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