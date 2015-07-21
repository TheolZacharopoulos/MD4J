package com.cwi.managed_data.examples.schemas;

import java.util.Date;

public interface Person {
    String name(String ...name);
    Integer age(Integer ...age);
    Date birthday(Date ...birthday);
}
