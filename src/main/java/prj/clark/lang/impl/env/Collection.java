package prj.clark.lang.impl.env;

import java.util.Iterator;

public abstract class Collection implements Data {
    public abstract Iterator<Data> iter();
}
