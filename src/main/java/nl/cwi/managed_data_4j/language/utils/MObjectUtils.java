package nl.cwi.managed_data_4j.language.utils;

import nl.cwi.managed_data_4j.language.schema.models.definition.M;
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

    public static boolean equals(Object x, Object y) {
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

        // TODO: get schemaKlass and check, HOW?! We need the MObject if we want this

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

            return xLen == yLen && // they should have the same size (structure)
                (xLen == 0 ||
                    areVectorsEqual(equalityMap, crossReferences, xVector, yVector, 0)); // if the len is 0 then true, otherwise compare
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
                logger.debug(" [Cross-Reference] <" + xFieldMethod.getName() + ">"); // Cross reference

                // support multiple values, add value to cross references
                if (!crossReferences.containsKey(xFieldValue)) {
                    crossReferences.put(xFieldValue, new LinkedList<>());
                }
                crossReferences.get(xFieldValue).add(yFieldValue);

                return areFieldsEqual(equalityMap, crossReferences, x, xFields, y, yFields, n + 1);
            }
            logger.debug(" [Contain] <" + xFieldMethod.getName() + ">"); // Spine

            return e(equalityMap, crossReferences, xFieldValue, yFieldValue) &&
                areFieldsEqual(equalityMap, crossReferences, x, xFields, y, yFields, n + 1);
        }
    }

    private static Class<?> getRealFieldClass(Object instance) {
        if (Proxy.isProxyClass(instance.getClass())) {
            return ((M) instance).schemaKlass().classOf();
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
