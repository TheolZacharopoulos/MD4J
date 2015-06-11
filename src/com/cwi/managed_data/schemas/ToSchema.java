package com.cwi.managed_data.schemas;

import sun.jvm.hotspot.oops.Klass;

import java.util.Set;

public class ToSchema {
    public static Schema convert(Class _interface) {
        return new Schema() {
            @Override
            public Set<Klass> classes(Klass... xs) {
                return null;
            }
        };
    }
}
