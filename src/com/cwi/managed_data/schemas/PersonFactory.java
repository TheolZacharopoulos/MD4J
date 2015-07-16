package com.cwi.managed_data.schemas;

import sun.util.calendar.BaseCalendar.Date;

public interface PersonFactory {
    Person person();
    Person person(String name, Integer age, Date birthday);
}