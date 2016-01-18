package nl.cwi.managed_data_4j.utils;

import nl.cwi.managed_data_4j.schema.models.definition.Field;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.schema.models.definition.Type;

import java.util.Set;

public class DebugUtils {

    public static void debugTypes(Set<Type> types) {
        System.out.println("=========================");
        for (Type type : types) {

            if (type instanceof Klass) {

                Klass klass = (Klass) type;
                System.out.println("*" + klass.name());

                // supers
                if (klass.supers() != null) {
                    for (Klass superKlass : klass.supers()) {
                        if (superKlass != null) {
                            System.out.println("  - Super: " + superKlass.name());
                        }
                    }
                }

                // subs
                if (klass.subklasses() != null) {
                    for (Klass subKlass : klass.subklasses()) {
                        System.out.println("  - Sub: " + subKlass.name());
                    }
                }

                // classOf
                if (klass.classOf() != null) {
                    System.out.println("  - classOf: " + klass.classOf().getName());
                }

                for (Field field : klass.fields()) {
                    System.out.println("\t" + field.name());

                    // type
                    if (field.type() == null) {
                        System.out.println("\t\t- Type : <<NULL>>");
                    } else {
                        System.out.println("\t\t- Type : " + field.type().name());
                        System.out.println("\t\t\t isPrimitive : " + PrimitiveUtils.isPrimitive(field.type().name()));
                    }

                    // owner
                    if (field.owner() == null) {
                        System.out.println("\t\t- Owner : <<NULL>>");
                    } else {
                        System.out.println("\t\t- Owner : " + field.owner().name());
                    }

                    // inverse
                    if (field.inverse() != null) {
                        System.out.println("\t\t- Inverse : " + field.inverse().name());
                    }

                    // many
                    System.out.println("\t\t- Many : " + field.many());

                    // optional
                    System.out.println("\t\t- Optional : " + field.optional());

                    // key
                    System.out.println("\t\t- Key : " + field.key());

                    // contain
                    System.out.println("\t\t- Contain : " + field.contain());
                }
            }
        }
    }
}
