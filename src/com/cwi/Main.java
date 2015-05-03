package com.cwi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;

interface Point {
    Integer x(Integer ...x);
    Integer y(Integer ...y);
}

class BasicRecordInvocationHandler implements InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        
        if (method.getName().equals("x")) {
            System.out.println("X!");

            Parameter[] params = method.getParameters();
            for (Parameter param : params) {
                System.out.println(param.getName());
            }

//            Field field = paramTypes[0].getField("x");
//            System.out.println(field.get(proxy));
        }
        
        if (method.getName().equals("y")) {
            System.out.println("Y!");
        }
        
        return null;
    }    
}

public class Main {

    public static void main(String[] args) {
        
        Point p = (Point)
                Proxy.newProxyInstance(
                        Point.class.getClassLoader(), 
                        new Class<?>[] {Point.class},
                        new BasicRecordInvocationHandler()
                );
        
        p.x(4);
        p.y();
    }
}