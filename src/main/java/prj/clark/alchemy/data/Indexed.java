package prj.clark.alchemy.data;

import java.util.Optional;

/**
 * This interface is for representing {@link Data} that may be directly indexed to obtain a single element.
 */
public interface Indexed extends Data {
    /**
     * Attempt to obtain a particular value given input data. If the data isn't in the correct format, the implementing
     * class may throw an exception.
     * @param index the key being used to index this object.
     * @return an {@link Optional} containing the result.
     */
    // TODO(matthew-c21) @throws
    Optional<Data> getIndex(Data index);
}
