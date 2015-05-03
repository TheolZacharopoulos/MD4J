package com.cwi;

public class Person {

    public String name = "";
    public int age;
    
    public Person() {
        this("NONAME", 0);
    }
    
    public Person(String _name, int _age) {
        name = _name;
        age = _age;
    }
    
    public String getName() {
        return name;
    }
    
    private int calcAge(String _date) {
        return 10;
    }
    
    public void setName(String _name) {
        name = _name;
    }
}
