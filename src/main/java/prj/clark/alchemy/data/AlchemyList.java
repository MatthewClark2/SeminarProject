package prj.clark.alchemy.data;

import java.util.Iterator;

public abstract class AlchemyList implements Sequenced, Sliceable, Printable, Chainable {
    @Override
    public String print() {
        StringBuilder sb = new StringBuilder("[");

        Iterator<Data> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next());

            if (it.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AlchemyList) {
            AlchemyList l = (AlchemyList) o;

            if (!l.terminates() || l.terminates() != this.terminates()) {
                return false;
            }

            Iterator<Data> thisIt = iterator();
            Iterator<Data> thatIt = l.iterator();

            while (thisIt.hasNext() && thatIt.hasNext()) {
                if (!thisIt.next().equals(thatIt.next())) {
                    return false;
                }
            }

            return ! (thisIt.hasNext() || thatIt.hasNext());
        }

        return false;
    }
}
