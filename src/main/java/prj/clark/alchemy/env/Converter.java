package prj.clark.alchemy.env;

/**
 * Superinterface for the set of classes that can be used to implicitly cast between types.
 */
public interface Converter {
    Data convert(Data data);
}
