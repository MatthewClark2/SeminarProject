package prj.clark.alchemy.data;

import java.util.Optional;

/**
 * This interface is used for {@link Data} capable of holding other attributes by their name. In most cases, attribute
 * names will need to conform to standard Alchemy identifier standards, although this isn't a contractual obligation of
 * the interface. Implementations that break this rule will need to specify that they do so.
 */
public interface HoldsAttribute extends Data {
    /**
     * Attempt to obtain a specific attribute by name.
     * @param attributeName the name of the attribute.
     * @return the result of searching for the attribute.
     */
    Optional<Data> getAttribute(String attributeName);
}
