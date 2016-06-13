package nl.cwi.managed_data_4j.language.primitives;

/**
 * The primitives defined in Schema
 */
public interface Primitives {
    Integer Integer();
    int _int();

    Boolean Boolean();
    boolean _boolean();

    String String();
    Class Class();
    Float Float();
    Double Double();

    int[] __intArr(); // for enums
}
