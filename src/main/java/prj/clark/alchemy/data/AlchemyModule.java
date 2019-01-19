package prj.clark.alchemy.data;

import java.util.Optional;

public class AlchemyModule implements HoldsAttribute {
    @Override
    public Optional<Data> getAttribute(String attributeName) {
        return Optional.empty();
    }
}
