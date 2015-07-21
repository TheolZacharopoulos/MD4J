package com.cwi.managed_data.examples.schemas;

import java.util.Date;

public interface PersonFactory {
    Person person();
    Person person(String name, Integer age, Date birthday);
}