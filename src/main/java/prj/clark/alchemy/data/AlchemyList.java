package prj.clark.alchemy.data;

import java.util.Iterator;

public interface AlchemyList extends Sequenced, Sliceable, Printable, Chainable {
    @Override
    default String print() {
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
}
