package nl.cwi.managed_data_4j.language.utils;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.many.MObjectFieldMany;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.single.MObjectFieldSingle;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.annotations.Contain;
import org.apache.log4j.LogManager;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

public class MObjectUtils {
    private static final org.apache.log4j.Logger logger = LogManager.getLogger(MObjectUtils.class.getName());

    public static String toString(Object mObj) {
        if (!Proxy.isProxyClass(mObj.getClass())) {
            throw new RuntimeException("Should check Managed Objects only.");
        }

        String mObjectToString = "";
        final MObject mObject = (MObject) Proxy.getInvocationHandler(mObj);
        final Klass schemaKlass = mObject.schemaKlass();

        mObjectToString += klassToString(mObject, schemaKlass);

        return mObjectToString;
    }

    private static String klassToString(MObject mObject, Klass klass) {
        String klassToString = "";
        klassToString += klass.name() + "\n";
        for (Field field : klass.fields()) {
            klassToString += fieldToString(mObject, field);
        }
        return klassToString;
    }

    private static String fieldToString(MObject mObject, Field field) {
        String fieldName = field.name();
        String fieldToString = "";
        fieldToString += "-" + fieldName;

        if (field.many()) {
            fieldToString += "[\n";
            final MObjectFieldMany values = (MObjectFieldMany) mObject.getMObjectField(fieldName);

            for (Object value : values) {

                if (value != null && Proxy.isProxyClass(value.getClass())) {
                    final MObject mValue = (MObject) Proxy.getInvocationHandler(value);

                    fieldToString += "\t" + mValue.getMObjectField("name").get() + "\n";
                } else {
                    fieldToString += value + "\n";
                }
            }
            fieldToString += "]\n";
        } else {
            final MObjectFieldSingle value = (MObjectFieldSingle) mObject.getMObjectField(fieldName);

            if (value != null && Proxy.isProxyClass(value.getClass())) {
                MObject mValue = (MObject) Proxy.getInvocationHandler(value);
                fieldToString += mValue.getMObjectField("name").get() + "\n";
            } else {
                fieldToString += value + "\n";
            }
        }

        return fieldToString;
    }

    public static boolean equals(Object x, Object y) {
        final Map<Object, Object> ht = new HashMap<>();
        return e(ht, x, y);
    }

    public static boolean e(Map<Object, Object> ht, Object x, Object y) {

        // TODO: get schemaKlass and check
        // TODO: 2 traversals

//        if (ht.get(x) == y) {
//            return true;
//        }

        // check for null first
        if (x == null && y == null) {
            logger.debug(" x and y are NULL");
            return true;
        } else if ((x == null && y != null) || (x != null && y == null)) {
            logger.debug(" one of x and y is NULL");
            return false;
        }

        // extract class
        final Class<?> xFieldClass = getRealFieldClass(x);
        final Class<?> yFieldClass = getRealFieldClass(y);

        // primitive leaf, just compare values
        final boolean xClassIsPrimitive = PrimitiveUtils.isPrimitiveClass(xFieldClass);
        final boolean yClassIsPrimitive = PrimitiveUtils.isPrimitiveClass(xFieldClass);
        if (xClassIsPrimitive && yClassIsPrimitive && xFieldClass.equals(yFieldClass)) {
            logger.debug(" << Primitive >> : (x = " + x + " | y = " + y + ")");
            return x.equals(y);
        }

        // vector leaf
        if (ArrayUtils.isMany(x.getClass()) && ArrayUtils.isMany(y.getClass())) {
            logger.debug(" << Vector >> ");
            final List<Object> xVector = new LinkedList<>((Collection<Object>) x);
            final List<Object> yVector = new LinkedList<>((Collection<Object>) y);
            final int xLen = xVector.size();
            final int yLen = yVector.size();

            return xLen == yLen && // they should have the same size (structure)
                (xLen == 0 || areVectorsEqual(ht, xVector, yVector, 0)); // if the len is 0 then true, otherwise compare
        }

        // Objects leaf
        List<Method> xFields = Arrays.asList(xFieldClass.getMethods());;
        logger.debug(" <<Object>> (x) : " + xFieldClass.getSimpleName());
        if (!Proxy.isProxyClass(xFieldClass)) {
            final List<Method> xAllFields = Arrays.asList(xFieldClass.getMethods());

            // remove native methods
            xFields = xAllFields.stream()
                .filter(method -> !method.getDeclaringClass().getName().startsWith("java."))
                .collect(Collectors.toList());
        }

        List<Method> yFields = Arrays.asList(yFieldClass.getMethods());;
        logger.debug(" <<Object>> (y) : " + yFieldClass.getSimpleName());
        if (!Proxy.isProxyClass(yFieldClass)) {
            final List<Method> yAllFields = Arrays.asList(yFieldClass.getMethods());

            // remove native methods
            yFields = yAllFields.stream()
                .filter(method -> !method.getDeclaringClass().getName().startsWith("java."))
                .collect(Collectors.toList());
        }

        // sort fields by name
        Collections.sort(xFields, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        Collections.sort(yFields, (o1, o2) -> o1.getName().compareTo(o2.getName()));

        return xFields.size() == yFields.size() && // they should have the same size (branch number)
            (xFields.size() == 0 || areFieldsEqual(ht, x, xFields, y, yFields, 0)); // if the len is 0 then true, otherwise compare
    }

    private static boolean areVectorsEqual(Map<Object, Object> ht, List<Object> xVector, List<Object> yVector, int n) {
        return ((xVector.size() == yVector.size()) && xVector.size() == n) ||
                e(ht, xVector.get(n), yVector.get(n)) && areVectorsEqual(ht, xVector, yVector, n + 1);
    }

    private static boolean areFieldsEqual(
            Map<Object, Object> ht,
            Object x, List<Method> xFields,
            Object y, List<Method> yFields,
            int n)
    {
        if (xFields.size() == n && xFields.size() == yFields.size()) {
            return true;
        } else {

            final Method xFieldMethod = xFields.get(n);
            final Method yFieldMethod = yFields.get(n);

            logger.debug("\t(x) Field name: " + xFieldMethod.getName());
            logger.debug("\t(y) Field name: " + yFieldMethod.getName());

            final Object xFieldValue = getValueFromMethod(x, xFieldMethod);
            final Object yFieldValue = getValueFromMethod(y, yFieldMethod);

            final boolean isFieldContain = xFieldMethod.isAnnotationPresent(Contain.class);

            final Class<?> xClass = xFieldMethod.getReturnType();
            final boolean isPrimitive = PrimitiveUtils.isPrimitiveClass(xClass);

            // Check Contain only for non primitives
            // So, if not primitive and not in Spine tree, just skip
            if (!isPrimitive && !isFieldContain) {
                logger.debug(" [Cross-Reference] "); // Cross reference

                // TODO: Check for the right cross-reference here
                boolean isTheRightReference = true;

                return isTheRightReference && areFieldsEqual(ht, x, xFields, y, yFields, n + 1);
            }
            logger.debug(" [Contain] "); // Spine

            return e(ht, xFieldValue, yFieldValue) && areFieldsEqual(ht, x, xFields, y, yFields, n + 1);
        }
    }

    private static Class<?> getRealFieldClass(Object instance) {
        if (Proxy.isProxyClass(instance.getClass())) {
            return ((MObject) Proxy.getInvocationHandler(instance)).schemaKlass().classOf();
        }
        return instance.getClass();
    }

    private static Object getValueFromMethod(Object instance, Method method) {
        try {

            // needs to be accessible in order to invoke it
            method.setAccessible(true);

            // in case the method needs parameters, we need to construct the parameter type
            if (method.getParameters().length > 0) {

                // We need the following in order to invoke the method with this kind of parameter
                // Get the parameter type, and get the first one
                final Class<?>[] parameterTypes = method.getParameterTypes();
                final Class<?> firstParameterType = parameterTypes[0];

                // In case of vargs, make it from array to single type
                // after, create an empty array of that type, this way we can invoke methods that need
                // empty vargs as parameters.
                Object emptyArrayOfFirstParameterType;
                if (firstParameterType.isArray()) {
                    emptyArrayOfFirstParameterType = Array.newInstance(firstParameterType.getComponentType(), 0);
                } else {
                    emptyArrayOfFirstParameterType = Array.newInstance(firstParameterType, 0);
                }

                return method.invoke(instance, emptyArrayOfFirstParameterType);
            } else {
                return method.invoke(instance);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
