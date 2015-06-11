package com.cwi.managed_data.schemas;

import sun.jvm.hotspot.oops.Klass;

import java.util.Set;

public interface Schema {
    Set<Klass> classes(Klass ...xs);
}
