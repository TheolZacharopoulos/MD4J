package com.cwi.managed_data.schemas;

import sun.util.calendar.BaseCalendar.Date;

public interface Person {
    String name(String ...name);
    Integer age(Integer ...age);
    Date birthday(Date ...birthday);
}
