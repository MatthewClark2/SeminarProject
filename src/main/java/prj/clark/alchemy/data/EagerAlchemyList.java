package prj.clark.alchemy.data;

import prj.clark.alchemy.err.TypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Optional;

public class EagerAlchemyList implements AlchemyList {
    private final List<Data> data;

    public EagerAlchemyList(List<Data> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public Iterator<Data> iterator() {
        return data.iterator();
    }

    @Override
    public boolean terminates() {
        return true;
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        // TODO(matthew-c21) - Fix thrown exceptions.
        // We specifically check for an AlchemyInt to avoid being able to use floats on accident.
        if (!(index instanceof Numeric) || !((Numeric) index).isInteger()) {
            throw new TypeMismatchException();
        }

        int i = (int) (((Numeric) index).intValue());

        if (i < 0) {
            throw new IllegalArgumentException();
        }

        return (i < this.data.size())
                ? Optional.of(this.data.get(i))
                : Optional.empty();
    }

    @Override
    public String toString() {
        return print();
    }

    @Override
    public boolean equals(Object o) {
        // TODO(matthew-c21) - Properly test this.
        if (o instanceof Sequenced) {
            Iterator a = ((Sequenced) o).iterator();
            Iterator i = iterator();
            while (i.hasNext() && a.hasNext()) {
                if (! a.next().equals(i.next())) {
                    return false;
                }
            }

            return ! (a.hasNext() || i.hasNext());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
