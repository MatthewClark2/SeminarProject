package prj.clark.alchemy.data;

/**
 * This interface is used for type checking of {@link Data} objects at runtime.
 */
// TODO(matthew-c21) - Deprecate and remove this interface and all implementing classes.
public interface DataType {
    boolean ofType(DataType dt);
}
