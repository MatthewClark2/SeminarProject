package prj.clark.alchemy.env;

import org.junit.Before;
import org.junit.Test;
import prj.clark.alchemy.data.*;
import prj.clark.alchemy.err.FunctionInvocationException;
import prj.clark.alchemy.err.IllegalRebindingException;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class AlchemyContextTest {
    private static final String[] FUNCTIONS = new String[]{
            "print", "input", "str", "int", "float", "pi", "e", "inf", "slurp", "spit", "write", "rand", "reverse"
    };

    private Context ctx;
    private OutputStream os;

    private Invokable getFunction(String identifier) {
        return (Invokable) (ctx.search(identifier).get());
    }

    @Before
    public void setUp() {
        InputStream is = new ByteArrayInputStream("some\ntest\ndata".getBytes());

        os = new ByteArrayOutputStream();
        ctx = new AlchemyContext(is, os);
    }

    @Test
    public void allFunctionsPresent() {
        for (String s : FUNCTIONS) {
            assertTrue(ctx.search(s).isPresent());
        }
    }

    @Test
    public void cannotRebindAnyFunctions() {
        int failedValueBinds = 0;
        int failedFunctionBinds = 0;

        for (String f : FUNCTIONS) {
            try {
                ctx.bind(f, null);
            } catch (IllegalRebindingException e) {
                failedValueBinds++;
            }

            try {
                ctx.bindFunction(f, null);
            } catch (IllegalRebindingException e) {
                failedFunctionBinds++;
            }
        }

        assertEquals(FUNCTIONS.length, failedValueBinds);
        assertEquals(FUNCTIONS.length, failedFunctionBinds);
    }

    @Test
    public void printBehavesCorrectly() {
        Invokable d = getFunction("print");

        assertTrue(d.invoke(Collections.emptyList()) instanceof Invokable);

        assertEquals(new AlchemyTuple(Collections.emptyList()), d.invoke(Collections.singletonList(AlchemyInt.of(1))));

        assertEquals(os.toString(), "1\n");
    }

    @Test(expected = FunctionInvocationException.class)
    public void printFailsCorrectly() {
        Invokable d = getFunction("print");

        d.invoke(Arrays.asList(AlchemyInt.of(1), null));
    }

    @Test
    public void inputBehavesCorrectly() {
        Invokable d = getFunction("input");

        assertTrue(d.invoke(Collections.emptyList()) instanceof Invokable);

        assertEquals(AlchemyString.of("some"), d.invoke(Collections.singletonList(AlchemyInt.of(1))));

        assertEquals(os.toString(), "1\n");

        assertEquals(AlchemyString.of("test"), d.invoke(Collections.singletonList(AlchemyInt.of(2))));

        assertEquals(os.toString(), "1\n2\n");

        assertEquals(AlchemyString.of("data"), d.invoke(Collections.singletonList(AlchemyInt.of(3))));

        assertEquals(os.toString(), "1\n2\n3\n");

        // Ensure that it can't read when the input is empty.
        try {
            d.invoke(Collections.singletonList(AlchemyString.of("h")));
            fail();
        } catch (IOError e) {
        }
    }

    @Test(expected = FunctionInvocationException.class)
    public void inputFailsCorrectly() {
        Invokable d = getFunction("input");

        d.invoke(Arrays.asList(AlchemyInt.of(1), null));
    }

    @Test(expected = FunctionInvocationException.class)
    public void strBehavesCorrectly() {
        Invokable d = getFunction("str");

        assertTrue(d.invoke(Collections.emptyList()) instanceof Invokable);

        assertEquals(AlchemyString.of("1"), d.invoke(Collections.singletonList(AlchemyInt.of(1))));
        assertEquals(AlchemyString.of("2.5"), d.invoke(Collections.singletonList(AlchemyFloat.of(2.5))));
        assertEquals(AlchemyString.of("True"), d.invoke(Collections.singletonList(AlchemyBoolean.of(true))));
        assertEquals(AlchemyString.of("[1, 2]"), d.invoke(Collections.singletonList(new EagerAlchemyList(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2))))));

        d.invoke(Arrays.asList(null, null));
    }

    @Test(expected = FunctionInvocationException.class)
    public void strFailsCorrectly() {
        Invokable d = getFunction("str");

        d.invoke(Arrays.asList(null, null));
    }

    @Test
    public void intBehavesCorrectly() {
        Invokable d = getFunction("int");

        assertTrue(d.invoke(Collections.emptyList()) instanceof Invokable);

        for (int i = -1; i < 2; ++i) {
            assertEquals(AlchemyInt.of(i), d.invoke(Collections.singletonList(AlchemyString.of("" + i))));
        }
    }

    @Test(expected = FunctionInvocationException.class)
    public void intFailsCorrectly() {
        Invokable d = getFunction("int");

        d.invoke(Arrays.asList(null, null));
    }

    @Test
    public void floatBehavesCorrectly() {
        Invokable d = getFunction("float");

        assertTrue(d.invoke(Collections.emptyList()) instanceof Invokable);

        for (double i = -0.5; i <= 0.5; i += 0.25) {
            assertEquals(AlchemyFloat.of(i), d.invoke(Collections.singletonList(AlchemyString.of("" + i))));
        }
    }

    @Test(expected = FunctionInvocationException.class)
    public void floatFailsCorrectly() {
        Invokable d = getFunction("float");

        d.invoke(Arrays.asList(null, null));
    }

    @Test
    public void piBehavesCorrectly() {
        Invokable d = getFunction("pi");

        assertEquals(AlchemyFloat.of(Math.PI), d.invoke(Collections.emptyList()));
    }

    @Test(expected = FunctionInvocationException.class)
    public void piFailsCorrectly() {
        Invokable d = getFunction("pi");
        d.invoke(Collections.singletonList(null));
    }

    @Test
    public void eBehavesCorrectly() {
        Invokable d = getFunction("e");

        assertEquals(AlchemyFloat.of(Math.E), d.invoke(Collections.emptyList()));
    }

    @Test(expected = FunctionInvocationException.class)
    public void eFailsCorrectly() {
        Invokable d = getFunction("e");
        d.invoke(Collections.singletonList(null));
    }

    @Test
    public void infBehavesCorrectly() {
        Invokable d = getFunction("inf");

        assertEquals(AlchemyFloat.of(Double.POSITIVE_INFINITY), d.invoke(Collections.emptyList()));
    }

    @Test(expected = FunctionInvocationException.class)
    public void infFailsCorrectly() {
        Invokable d = getFunction("inf");
        d.invoke(Collections.singletonList(null));
    }

    @Test
    public void slurpBehavesCorrectly() throws IOException {
        Invokable d = getFunction("slurp");
        assertTrue(d.invoke(Collections.emptyList()) instanceof Invokable);

        File f = File.createTempFile("slurpBehavesCorrectly", "test");
        f.deleteOnExit();

        BufferedWriter br = new BufferedWriter(new FileWriter(f));

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; ++i) {
            sb.append(Math.random());
        }

        br.write(sb.toString());
        br.close();

        assertEquals(AlchemyString.of(sb.toString()), d.invoke(Collections.singletonList(AlchemyString.of(f.getAbsolutePath()))));
    }

    @Test(expected = FunctionInvocationException.class)
    public void slurpFailsCorrectly() {
        Invokable d = getFunction("slurp");

        d.invoke(Arrays.asList(null, null));
    }

    @Test
    public void spitBehavesCorrectly() throws IOException {
        Invokable d = getFunction("spit");
        assertTrue(d.invoke(Collections.emptyList()) instanceof Invokable);
        assertTrue(d.invoke(Collections.singletonList(null)) instanceof Invokable);

        File f = File.createTempFile("spitBehavesCorrectly", "test");
        f.deleteOnExit();

        BufferedWriter br = new BufferedWriter(new FileWriter(f));

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; ++i) {
            sb.append(Math.random());
        }

        br.write(sb.toString());
        br.close();

        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 5; ++i) {
            sb2.append(Math.random());
        }

        assertEquals(new AlchemyTuple(Collections.emptyList()), d.invoke(Arrays.asList(AlchemyString.of(f.getAbsolutePath()), AlchemyString.of(sb2.toString()))));
        assertEquals(sb.append(sb2).toString(), new String(Files.readAllBytes(f.toPath())));
    }

    @Test(expected = FunctionInvocationException.class)
    public void spitFailsCorrectly() {
        Invokable d = getFunction("spit");

        d.invoke(Arrays.asList(null, null, null));
    }

    @Test
    public void writeBehavesCorrectly() throws IOException {
        Invokable d = getFunction("write");
        assertTrue(d.invoke(Collections.emptyList()) instanceof Invokable);
        assertTrue(d.invoke(Collections.singletonList(null)) instanceof Invokable);

        File f = File.createTempFile("writeBehavesCorrectly", "test");
        f.deleteOnExit();

        BufferedWriter br = new BufferedWriter(new FileWriter(f));

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; ++i) {
            sb.append(Math.random());
        }

        br.write(sb.toString());
        br.close();

        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 5; ++i) {
            sb2.append(Math.random());
        }

        assertEquals(new AlchemyTuple(Collections.emptyList()), d.invoke(Arrays.asList(AlchemyString.of(f.getAbsolutePath()), AlchemyString.of(sb2.toString()))));
        assertEquals(sb2.toString(), new String(Files.readAllBytes(f.toPath())));
    }

    @Test(expected = FunctionInvocationException.class)
    public void writeFailsCorrectly() {
        Invokable d = getFunction("write");

        d.invoke(Arrays.asList(null, null, null));
    }

    @Test
    public void reverseBehavesCorrectly() {
        Data coll1 = new EagerAlchemyList(Arrays.asList(AlchemyInt.of(1), AlchemyInt.of(2), AlchemyInt.of(3)));
        Data coll2 = new EagerAlchemyList(Arrays.asList(AlchemyFloat.of(1.5), AlchemyInt.of(1), AlchemyString.of("hello"), AlchemyBoolean.TRUE));

        Data expected1 = new EagerAlchemyList(Arrays.asList(AlchemyInt.of(3), AlchemyInt.of(2), AlchemyInt.of(1)));
        Data expected2 = new EagerAlchemyList(Arrays.asList(AlchemyBoolean.TRUE, AlchemyString.of("hello"), AlchemyInt.of(1), AlchemyFloat.of(1.5)));

        Invokable d = getFunction("reverse");

        assertTrue(d.invoke(Collections.emptyList()) instanceof Invokable);

        assertEquals(expected1, d.invoke(Collections.singletonList(coll1)));
        assertEquals(expected2, d.invoke(Collections.singletonList(coll2)));
    }

    @Test(expected = FunctionInvocationException.class)
    public void reverseFailsCorrectly() {
        Invokable d = getFunction("reverse");

        d.invoke(Arrays.asList(null, null));
    }

    @Test
    public void randBehavesCorrectly() {
        Invokable d = getFunction("rand");

        for (int i = 0; i < 10; ++i) {
            Data f = d.invoke(Collections.emptyList());
            assertTrue(f instanceof Numeric);
            assertFalse(((Numeric)f).isInteger());
            assertTrue(((Numeric)f).floatValue() >= 0);
            assertTrue(((Numeric)f).floatValue() < 1);
        }
    }

    @Test(expected = FunctionInvocationException.class)
    public void randFailsCorrectly() {
        Invokable d = getFunction("rand");

        d.invoke(Collections.singletonList(null));
    }

    @Test
    public void ableToFindBoundValues() {
        ctx.bind("foo", AlchemyBoolean.TRUE);
        assertTrue(ctx.search("foo").isPresent());
        assertEquals(AlchemyBoolean.TRUE, ctx.search("foo").get());
    }

    @Test
    public void ableToFindBoundFunctions() {
        ctx.bindFunction("i", EagerAlchemyList::new);
        assertTrue(ctx.search("i").isPresent());
    }

    @Test(expected = IllegalRebindingException.class)
    public void mayNotBindIdUsedForFunction() {
        ctx.bindFunction("i", EagerAlchemyList::new);
        ctx.bind("i", AlchemyInt.of(3));
    }
}