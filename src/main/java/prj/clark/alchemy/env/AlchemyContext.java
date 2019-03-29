package prj.clark.alchemy.env;

import prj.clark.alchemy.data.*;
import prj.clark.alchemy.err.FunctionInvocationException;
import prj.clark.alchemy.err.LangException;
import prj.clark.alchemy.err.TypeMismatchException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Wraps a standard {@link DefaultContext} and populates it with pre-determined global values. Note that only global
 * functions are actually immutable, which is why most constants are wrapped as functions.
 *
 * Bound functions include:
 * - print(x): prints the string value to the stdout
 * - input(x): prints the given string, then reads a line from stdin.
 * - str(x): obtains the string representation of its argument.
 * - int(x): converts the argument to an integer.
 * - float(x): converts the argument to a float.
 * - pi(): returns pi.
 * - e(): returns e.
 * - inf(): returns positive infinity.
 * - rand(): returns a random float in [0, 1).
 * - slurp(f): reads a file as a single string.
 * - spit(f, x): appends the value x to the file f.
 * - write(f, x): overwrites the contents of the file f with the value of x.
 * - reverse(coll): Reverses a collection of elements, or throws a generic LangException if the sequence doesn't terminate.
 */
public class AlchemyContext implements Context {
    private final Context ctx;
    private final OutputStream out;
    private final Scanner in;

    private static class SingleArgumentInvokable implements Invokable {
        Function<Data, Data> f;

        SingleArgumentInvokable(Function<Data, Data> f) {
            this.f = f;
        }

        @Override
        public Data invoke(List<Data> args) {
            if (args.size() > 1) {
                throw new FunctionInvocationException();
            } else if (args.size() == 1) {
                return f.apply(args.get(0));
            }
            return this;
        }

        @Override
        public int parameterCount() {
            return 1;
        }
    }

    private static class DoubleArgumentInvokable implements Invokable {
        BiFunction<Data, Data, Data> f;

        DoubleArgumentInvokable(BiFunction<Data, Data, Data> f) {
            this.f = f;
        }

        @Override
        public Data invoke(List<Data> args) {
            if (args.size() > 2) {
                throw new FunctionInvocationException();
            } else if (args.size() == 2) {
                return f.apply(args.get(0), args.get(1));
            } else if (args.size() == 1) {
                return new SingleArgumentInvokable(x -> f.apply(args.get(0), x));
            }
            return this;
        }

        @Override
        public int parameterCount() {
            return 2;
        }
    }

    private static class ConstantExpression implements Invokable {
        private final Data d;

        ConstantExpression(Data d) {
            this.d = d;
        }

        @Override
        public Data invoke(List<Data> args) {
            if (args.size() > 0) {
                throw new FunctionInvocationException();
            }
            return d;
        }

        @Override
        public int parameterCount() {
            return 0;
        }
    }

    public AlchemyContext() {
        this(System.in, System.out);
    }

    public AlchemyContext(OutputStream out) {
        this(System.in, out);
    }

    public AlchemyContext(InputStream in) {
        this(in, System.out);
    }

    public AlchemyContext(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = out;
        ctx = new DefaultContext();
        initialize();
    }

    private void initialize() {
        // TODO(matthew-c21) - Test all of these functions.
        ctx.bindFunction("print", new SingleArgumentInvokable(x -> {
            try {
                BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out));
                br.write(x + "\n");
                br.close();
                return new AlchemyTuple(Collections.emptyList());
            } catch (IOException e) {
                throw new IOError(e);
            }
        }));

        ctx.bindFunction("input", new SingleArgumentInvokable(x -> {
            try {
                BufferedWriter br = new BufferedWriter(new OutputStreamWriter(out));
                br.write(x + "\n");
                br.close();
                return AlchemyString.of(in.nextLine());
            } catch (IOException e) {
                throw new IOError(e);
            }
        }));

        ctx.bindFunction("str", new SingleArgumentInvokable(x -> AlchemyString.of(x.toString())));
        ctx.bindFunction("int", new SingleArgumentInvokable(x -> AlchemyInt.of(x.toString())));
        ctx.bindFunction("float", new SingleArgumentInvokable(x -> AlchemyFloat.of(x.toString())));

        ctx.bindFunction("pi", new ConstantExpression(AlchemyFloat.of(Math.PI)));
        ctx.bindFunction("e", new ConstantExpression(AlchemyFloat.of(Math.E)));
        ctx.bindFunction("inf", new ConstantExpression(AlchemyFloat.of(Double.POSITIVE_INFINITY)));

        ctx.bindFunction("rand", args -> {
            if (args.size() > 0) {
                throw new FunctionInvocationException();
            }

            return AlchemyFloat.of(Math.random());
        });

        ctx.bindFunction("slurp", new SingleArgumentInvokable(x -> {
            try {
                return AlchemyString.of(new String(Files.readAllBytes(Paths.get(x.toString()))));
            } catch (IOException e) {
                throw new IOError(e);
            }
        }));
        ctx.bindFunction("spit", new DoubleArgumentInvokable((x, y) -> {
            try {
                File f = new File(x.toString());
                FileWriter fr = new FileWriter(f, true);
                BufferedWriter br = new BufferedWriter(fr);
                br.write(y.toString());
                br.close();
                fr.close();
            } catch (IOException e) {
                throw new IOError(e);
            }
            return new AlchemyTuple(Collections.emptyList());
        }));
        ctx.bindFunction("write", new DoubleArgumentInvokable((x, y) -> {
            try {
                File f = new File(x.toString());
                FileWriter fr = new FileWriter(f, false);
                BufferedWriter br = new BufferedWriter(fr);
                br.write(y.toString());
                br.close();
                fr.close();
            } catch (IOException e) {
                throw new IOError(e);
            }
            return new AlchemyTuple(Collections.emptyList());
        }));

        ctx.bindFunction("reverse", new SingleArgumentInvokable(x -> {
            if (!(x instanceof Sequenced)) {
                throw new TypeMismatchException();
            } else if (!((Sequenced) x).terminates()) {
                throw new RuntimeException("Sequence never terminates.");
            }
            ArrayList<Data> a = new ArrayList<>();
            ((Sequenced) x).iterator().forEachRemaining(d -> a.add(0, d));
            return new EagerAlchemyList(a);
        }));
    }

    @Override
    public void bind(String identifier, Data d) {
        ctx.bind(identifier, d);
    }

    @Override
    public void importModule() {
        ctx.importModule();
    }

    @Override
    public void bindFunction(String identifier, Invokable i) {
        ctx.bindFunction(identifier, i);
    }

    @Override
    public Optional<Data> search(String identifier) {
        return ctx.search(identifier);
    }
}
