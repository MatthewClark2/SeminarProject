package prj.clark.alchemy.data;

/**
 * This interface is used to represent collection type {@link Data} that can be represented as a smaller portion of
 * itself.
 * For example, [1, 2, 3] can be sliced into the smaller sequence [2, 3].
 */
public interface Sliceable extends Indexed, Data {
    /**
     * Obtain a portion of the current collection. The slice will contain all elements from the starting index up to,
     * but not including, the end index. Only every nth element will be collected into the resulting sequence.
     * @param start represents the start of the indexed set. This value should not be negative.
     * @param end represents the ending index, non-inclusively. If this value is less than or equal to start, then the
     *            resulting sequence will be empty.This value should not be negative.
     * @param n the skip value. This value should not be negative.
     * @return a sequence containing the collected elements.
     */
     // TODO(matthew-c21) @throws
    Sequenced slice(long start, long end, long n);
}
