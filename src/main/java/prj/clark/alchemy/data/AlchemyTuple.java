package prj.clark.alchemy.data;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class AlchemyTuple implements Sequenced, Indexed, Printable {
    private final AlchemyList data;

    public AlchemyTuple(List<Data> data) {
        this.data = new EagerAlchemyList(data);
    }

    @Override
    public Optional<Data> getIndex(Data index) {
        return data.getIndex(index);
    }

    @Override
    public String print() {
        StringBuilder sb = new StringBuilder("(");

        Iterator<Data> it = data.iterator();

        while (it.hasNext()) {
            sb.append(it.next());

            if (it.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append(")");

        return sb.toString();
    }

    @Override
    public String toString() {
        return print();
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
    public boolean equals(Object o) {
        if (o instanceof AlchemyTuple) {
            return ((AlchemyTuple) o).data.equals(this.data);
        }

        return false;
    }
}
