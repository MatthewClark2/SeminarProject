package prj.clark.lang.basic.env;

import javax.script.SimpleBindings;
import java.util.Map;

/**
 * Simple bindings implementation for the extremely basic example language provided within this package.
 * As a side note, I really wish that {@link javax.script.Bindings} didn't implement Map directly.
 */
public class BasicBindings extends SimpleBindings {
    @Override
    public void clear() {
        throw new UnsupportedOperationException("Basic bindings do not support deletion of elements.");
    }

    @Override
    public Object remove(Object o) {
        throw new UnsupportedOperationException("Basic bindings do not support deletion of elements.");
    }

    @Override
    public Object put(String s, Object o) {
        if (! (o instanceof BasicData)) {
            throw new IllegalArgumentException(o + " is not a valid data type.");
        }

        return super.put(s, o);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        map.forEach(this::put);
    }
}
