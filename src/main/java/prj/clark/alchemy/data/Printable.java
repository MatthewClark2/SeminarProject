package prj.clark.alchemy.data;

/**
 * This interface represents {@link Data} that has a defined string representation. Note that the result of
 * {@link #toString()} should be the same as the result of {@link #print()}. The primary purpose of this interface is
 * to distinguish between data that can be printed as an in-language object, and data that can't.
 */
public interface Printable extends Data {
    String print();
}
