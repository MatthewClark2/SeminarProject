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
    public Sequenced slice(Numeric start, Numeric end, Numeric n) {
        // Use a lambda to wrap the production of a SliceIterator.
        return () -> new SliceIterator(start, end, n, this);
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
    public String print() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < data.size() - 1; ++i) {
            sb.append(data.get(i));
            sb.append(", ");
        }

        sb.append(data.get(data.size() - 1));
        sb.append("]");

        return sb.toString();
    }

    @Override
    public String toString() {
        return print();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Sequenced) {
            Iterator a = ((Sequenced) o).iterator();
            for (Iterator i = iterator(); i.hasNext() && a.hasNext();) {
                if (! a.next().equals(i.next())) {
                    return false;
                }
            }

            return ! a.hasNext();
        }

        return false;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
