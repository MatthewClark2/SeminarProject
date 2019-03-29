package prj.clark.alchemy.data;

import java.util.Iterator;

/**
 * This interface represents {@link Data} that can be iterated over. Produced iterators may be either lazily or eagerly
 * generated, although the latter is preferred when possible.
 *
 * Produced iterators should NOT implement any mutating operations available to {@link Iterator}.
 */
public interface Sequenced extends Data, Printable, Iterable<Data> {
    /**
     * Produce a new iterator that doesn't allow for deletion of underlying members.
     * @return a new iterator.
     */
    Iterator<Data> iterator();

    /**
     * Determines whether or not a sequence can be calculated in its entirety.
     * @return true if the sequence has a terminating condition, and false otherwise.
     */
    boolean terminates();

    default String print() {
        return toString();
    }
}
