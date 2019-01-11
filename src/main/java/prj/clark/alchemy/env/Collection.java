package prj.clark.alchemy.env;

import java.util.Iterator;

public abstract class Collection implements Data {
    public abstract Iterator<Data> iter();
}
