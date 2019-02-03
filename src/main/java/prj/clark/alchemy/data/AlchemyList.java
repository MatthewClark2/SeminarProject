package prj.clark.alchemy.data;

import prj.clark.alchemy.err.TypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Optional;

public class AlchemyList implements Sequenced, Sliceable, Printable {
    private final List<Data> data;

    public AlchemyList(List<Data> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public Iterator iter() {
        return data.iterator();
    }

    @Override
    public Sequenced slice(long start, long end, long n) {
        // TODO(matthew-c21) - Implement using a lazy sequence.
        List<Data> part = new ArrayList<>();

        // TODO(matthew-c21) - Check for thrown exceptions.
        for (long i = start; i < end && i < data.size(); i += n) {
            // This cast is safe since i is always less than the size of data.
            part.add(data.get((int) i));
        }

        return new AlchemyList(part);
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        // TODO(matthew-c21) - Fix thrown exceptions.
        // We specifically check for an AlchemyInt to avoid being able to use floats on accident.
        if (! (index instanceof AlchemyInt)) {
            throw new TypeMismatchException();
        }

        int i = (int) (((Numeric) data).intValue());

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
            Iterator a = ((Sequenced) o).iter();
            for (Iterator i = iter(); i.hasNext() && a.hasNext();) {
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
