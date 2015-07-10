package com.cwi.managed_data.schemas;

public @interface Inverse {
    Class other();
    String field();
}
