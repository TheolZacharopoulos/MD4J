package com.cwi;

import java.lang.reflect.*;

public class ReflectionExamples {

    public static void main(String[] args)
            throws ClassNotFoundException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            NoSuchFieldException
    {
        Class cPersonByName = Class.forName("com.cwi.Person");

        Class cPersonClass = Person.class;
        String PersonName = Person.class.getName();
        Class cPersonSuperClass = cPersonClass.getSuperclass();
        Class [] cPersonInterfaces = cPersonClass.getInterfaces();

        System.out.println(PersonName);
        System.out.println(cPersonByName.getSimpleName());

        int modifiers = cPersonClass.getModifiers();
        System.out.println("Modifiers: " + modifiers);

        System.out.println("isPublic: " + Modifier.isPublic(modifiers));

        System.out.println("SuperClas: " + cPersonSuperClass.getSimpleName());

        System.out.println("Interfaces: ");
        for (Class cPersonInterface : cPersonInterfaces) {
            System.out.println("\t" + cPersonInterface.getName());
        }

        System.out.println("Constructors: ");
        Constructor[] constructors = cPersonClass.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println("\t" + constructor.getName() + " " + constructor.getParameterCount());
        }

        System.out.println("Methods: ");
        Method[] methods = cPersonClass.getMethods();
        for (Method method : methods) {
            System.out.println("\t" + Modifier.toString(method.getModifiers()) + " " + method.getName());
        }

        System.out.println("Fields: ");
        Field[] fields = cPersonClass.getFields();
        for (Field field : fields) {
            System.out.println("\t" + field.getName());
        }

        Constructor personConstructor = Person.class.getConstructor(new Class[] {String.class, int.class});
        Class [] parameterTypes = personConstructor.getParameterTypes();

        System.out.println("Constructor Parameter types:");
        for (Class parameterType : parameterTypes) {
            System.out.println("\t" + parameterType.getName());
        }

        // Constructor ----
        Person p = (Person) personConstructor.newInstance("Alex", 25);
        System.out.println(p.getName());

        Constructor emptyPersonConstructor = Person.class.getConstructor();
        Person p1 = (Person) emptyPersonConstructor.newInstance();
        System.out.println(p1.getName());

        // Field ----
        Field nameField = Person.class.getField("name");
        String initialName = (String) nameField.get(p1);
        System.out.println(initialName);
        nameField.set(p1, "test");
        System.out.println(p1.getName());


        // Method ---
        Method setNameMethod = Person.class.getMethod("setName", String.class);
        Method getNameMethod = Person.class.getMethod("getName", null);

        Class [] setNameMethodParams = setNameMethod.getParameterTypes();
        Class setNameMethodReturn = setNameMethod.getReturnType();

        setNameMethod.invoke(p1, "INVOKE");

        System.out.println(p1.getName());
        
        
        // Class loader ---
        ClassLoader classLoader = Person.class.getClassLoader();
        Class cPerson = classLoader.loadClass("com.cwi.Person");
        Constructor cP = cPerson.getConstructor();
        Person p2 = (Person) cP.newInstance();
        System.out.println(p2.getName());
    }
}
