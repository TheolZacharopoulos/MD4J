package nl.cwi.managed_data_4j.language.utils;

import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.M;
import nl.cwi.managed_data_4j.language.schema.models.definition.Type;
import org.apache.log4j.LogManager;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

public class MObjectUtils {
    private static final org.apache.log4j.Logger logger = LogManager.getLogger(MObjectUtils.class.getName());

    public static boolean equals(Object x, Object y) {

        if (!(x instanceof M) || !(y instanceof M)) {
            logger.info("Equality works on managed objects only");
            return false;
        }

        final Map<Object, Object> equalityMap = new HashMap<>();
        final Map<Object, List<Object>> crossReferences = new HashMap<>();

        return e(equalityMap, crossReferences, x, y) // Check first only the spine tree
            && crossReferencesCheck(equalityMap, crossReferences); // Then check cross references
    }

    private static boolean crossReferencesCheck(Map<Object, Object> equalityMap, Map<Object, List<Object>> crossReferences) {
        logger.debug(" Checking cross-references ");

        for (Object crossReferenceObject : crossReferences.keySet()) {
            for (Object equalityCheckedObject : equalityMap.keySet()) {

                // if a cross-reference that has been already checked is found
                if (crossReferenceObject == equalityCheckedObject) {
                    // then check their equivalence
                    final Object equalityValue = equalityMap.get(equalityCheckedObject);
                    final List<Object> crossRefValues = crossReferences.get(crossReferenceObject);

                    boolean crossRefValueFound = false;
                    for (Object crossRefValue : crossRefValues) {
                        if (equalityValue == crossRefValue) {
                            crossRefValueFound = true;
                            break;
                        }
                    }

                    if (!crossRefValueFound) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean e(Map<Object, Object> equalityMap, Map<Object, List<Object>> crossReferences, Object x, Object y) {

        // check for null first
        if (x == null && y == null) {
            logger.debug(" x and y are NULL");
            return true;
        } else if ((x == null && y != null) || (x != null && y == null)) {
            logger.debug(" one of x and y is NULL");
            return false;
        }

        // primitive leaf, just compare values
        if (PrimitiveUtils.isPrimitiveClass(x.getClass()) && PrimitiveUtils.isPrimitiveClass(y.getClass())
            && x.getClass().equals(y.getClass()))
        {
            logger.debug(" << Primitive >> : (x = " + x + " | y = " + y + ")");
            equalityMap.put(x, y);
            return x.equals(y);
        }

        // vector leaf
        if (ArrayUtils.isMany(x.getClass()) && ArrayUtils.isMany(y.getClass())) {
            logger.debug(" << Vector >> ");
            final List<Object> xVector = new LinkedList<>((Collection<Object>) x);
            final List<Object> yVector = new LinkedList<>((Collection<Object>) y);
            final int xLen = xVector.size();
            final int yLen = yVector.size();

            return
                xLen == yLen && // they should have the same size (structure)
                (xLen == 0 || areVectorsEqual(equalityMap, crossReferences, xVector, yVector, 0)); // if the len is 0 then true, otherwise compare
        }

        // Managed Objects leaf
        final M mObjectX = ((M) x);
        final Klass xKlass = mObjectX.schemaKlass();

        final M mObjectY = ((M) y);
        final Klass yKlass = mObjectY.schemaKlass();

        final List<Field> xFields = new LinkedList<>(xKlass.fields());
        logger.debug(" <<Object>> (x) : " + xKlass.name());

        final List<Field> yFields = new LinkedList<>(yKlass.fields());
        logger.debug(" <<Object>> (y) : " + yKlass.name());

        // sort fields by name, this way we know we compare the field with the right order
        Collections.sort(xFields, (o1, o2) -> o1.name().compareTo(o2.name()));
        Collections.sort(yFields, (o1, o2) -> o1.name().compareTo(o2.name()));

        boolean areFieldsEqual = xFields.size() == yFields.size() && // they should have the same size (branch number)
            (xFields.size() == 0 ||
            areFieldsEqual(equalityMap, crossReferences, x, xFields, y, yFields, 0)); // if the len is 0 then true, otherwise compare

        equalityMap.put(x, y);

        return areFieldsEqual;
    }

    private static boolean areVectorsEqual(
        Map<Object, Object> equalityMap, Map<Object, List<Object>> crossReferences,
        List<Object> xVector, List<Object> yVector, int n) {
        return ((xVector.size() == yVector.size()) && xVector.size() == n) ||
                e(equalityMap, crossReferences, xVector.get(n), yVector.get(n))
                    && areVectorsEqual(equalityMap, crossReferences, xVector, yVector, n + 1);
    }

    private static boolean areFieldsEqual(
            Map<Object, Object> equalityMap,
            Map<Object, List<Object>> crossReferences,
            Object x, List<Field> xFields,
            Object y, List<Field> yFields,
            int n)
    {
        if (xFields.size() == n && xFields.size() == yFields.size()) {
            return true;
        } else {

            final Field xField = xFields.get(n);
            final Field yField = yFields.get(n);

            logger.debug("\t(x) Field name: " + xField.name());
            logger.debug("\t(y) Field name: " + yField.name());

            final Object xFieldValue = getValueFromField(x, xField);
            final Object yFieldValue = getValueFromField(y, yField);

            final boolean isPrimitive = PrimitiveUtils.isPrimitiveClass(xField.type().classOf());

            // Check Contain only for non primitives
            // So, if not primitive and not in Spine tree, just skip
            if (!isPrimitive && !(xField.contain() || yField.contain())) {
                logger.debug(" [Cross-Reference] <" + xField.name() + ">"); // Cross reference

                // support multiple values, add value to cross references
                if (!crossReferences.containsKey(xFieldValue)) {
                    crossReferences.put(xFieldValue, new LinkedList<>());
                }
                crossReferences.get(xFieldValue).add(yFieldValue);

                return areFieldsEqual(equalityMap, crossReferences, x, xFields, y, yFields, n + 1);
            }
            logger.debug(" [Contain] <" + xField.name() + ">"); // Spine

            return e(equalityMap, crossReferences, xFieldValue, yFieldValue) &&
                areFieldsEqual(equalityMap, crossReferences, x, xFields, y, yFields, n + 1);
        }
    }

    private static Object getValueFromField(Object instance, Field field) {
        try {

            Method method;
            try {
                // try with a method without params
                method = instance.getClass().getMethod(field.name());
            } catch (NoSuchMethodException e) {

                // if it does not work, get the params.
                Class<?> parameterType = Array.newInstance(((Type)field.type()).classOf(), 0).getClass();
                method = instance.getClass().getMethod(field.name(), parameterType);
            }

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

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
