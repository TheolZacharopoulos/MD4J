package com.cwi.managed_data;

public class PointLogger {
    static public void log(String name, Object _value) {
        System.out.println("\t Updating " + name + " to " + _value.toString());
    }
}