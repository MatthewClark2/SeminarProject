package prj.clark.alchemy.data;

/**
 * This interface is used to represent collection type {@link Data} that can be represented as a smaller portion of
 * itself. It is merely a contract that says that a sequence may be sliced, and does not specify any behavior.
 * For example, [1, 2, 3] can be sliced into the smaller sequence [2, 3].
 */
public interface Sliceable extends Sequenced, Indexed, Data {
}
