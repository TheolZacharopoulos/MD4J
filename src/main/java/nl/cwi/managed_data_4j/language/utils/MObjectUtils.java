package nl.cwi.managed_data_4j.language.utils;

import java.util.*;

import nl.cwi.managed_data_4j.M;
import nl.cwi.managed_data_4j.language.primitives.PrimitivesManager;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import nl.cwi.managed_data_4j.language.schema.models.definition.Type;

public class MObjectUtils {
    private static final PrimitivesManager primitiveManager = PrimitivesManager.getInstance();

    public static boolean instanceOf(M object, Class<?> clazz) {
        return object.schemaKlass().name().equals(clazz.getSimpleName());
    }

    public static boolean equals(M x, M y) {

        final Map<Object, Object> equalityMap = new HashMap<>();
        final Map<Object, List<Object>> crossReferences = new HashMap<>();

        return e(equalityMap, crossReferences, x, y) // Check first only the spine tree
            && crossReferencesCheck(equalityMap, crossReferences); // Then check cross references
    }

    private static boolean crossReferencesCheck(Map<Object, Object> equalityMap, Map<Object, List<Object>> crossReferences) {
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

    private static boolean e(Map<Object, Object> equalityMap, Map<Object, List<Object>> crossReferences, Object x, Object y) {

        // check for null first
        if (x == null && y == null) {
            // System.out.println(" x and y are NULL");
            return true;
        } else if ((x == null && y != null) || (x != null && y == null)) {
            // System.out.println(" one of x and y is NULL");
            return false;
        }

        // primitive leaf, just compare values
        if (primitiveManager.isPrimitiveClass(x.getClass()) &&
            primitiveManager.isPrimitiveClass(y.getClass()) &&
            x.getClass().equals(y.getClass()))
        {
            // System.out.println(" << Primitive >> : (x = " + x + " | y = " + y + ")");
            equalityMap.put(x, y);
            return x.equals(y);
        }

        // vector leaf
        if (primitiveManager.isMany(x.getClass()) && primitiveManager.isMany(y.getClass())) {
            // System.out.println(" << Vector >> ");

            final Collection<Object> xCollection = (Collection<Object>) x;
            final Collection<Object> yCollection = (Collection<Object>) y;
            final List<Object> xVector = new LinkedList<>(xCollection);
            final List<Object> yVector = new LinkedList<>(yCollection);
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
        // System.out.println(" <<Object>> (x) : " + xKlass.name());

        final List<Field> yFields = new LinkedList<>(yKlass.fields());
        // System.out.println(" <<Object>> (y) : " + yKlass.name());

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
        Map<Object, Object> equalityMap,
        Map<Object, List<Object>> crossReferences,
        List<Object> xVector,
        List<Object> yVector,
        int n)
    {
        // end
        if (((xVector.size() == yVector.size()) && xVector.size() == n)) {
            return true;
        }

        // in case the vector contains M objects and its a set,
        // then we need to order them in order to check for equality.
        // Sort the vectors based on the field's name
        if (xVector.size() > 0 && xVector.get(0) instanceof M) {
            Collections.sort(xVector, ReflectionUtils.fieldNameComparison());
            Collections.sort(yVector, ReflectionUtils.fieldNameComparison());
        }

        // continue
        return e(equalityMap, crossReferences, xVector.get(n), yVector.get(n)) &&
            areVectorsEqual(equalityMap, crossReferences, xVector, yVector, n + 1);
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

            // System.out.println("\t(x) Field name: " + xField.name());
            // System.out.println("\t(y) Field name: " + yField.name());

            final Object xFieldValue = getValueFromField(x, xField.name(), xField.type());
            final Object yFieldValue = getValueFromField(y, yField.name(), yField.type());

            final boolean isPrimitive = xField.type().schemaKlass().name().equals("Primitive");

            // Check Contain only for non primitives
            // So, if not primitive and not in Spine tree, just skip
            if (!isPrimitive && !(xField.contain() || yField.contain())) {
                // System.out.println(" [Cross-Reference] <" + xField.name() + ">"); // Cross reference

                // support multiple values, add value to cross references
                if (!crossReferences.containsKey(xFieldValue)) {
                    crossReferences.put(xFieldValue, new LinkedList<>());
                }
                crossReferences.get(xFieldValue).add(yFieldValue);

                return areFieldsEqual(equalityMap, crossReferences, x, xFields, y, yFields, n + 1);
            }
            // System.out.println(" [Contain] <" + xField.name() + ">"); // Spine

            return e(equalityMap, crossReferences, xFieldValue, yFieldValue) &&
                areFieldsEqual(equalityMap, crossReferences, x, xFields, y, yFields, n + 1);
        }
    }

    private static Object getValueFromField(Object instance, String fieldName, Type fieldType) {
        try {
            return ReflectionUtils.getValueFromField(instance, fieldName, fieldType.classOf());
        } catch (Throwable e) {
            System.err.println("Error on getting field's value: " + e.getCause());
        }
        return null;
    }
}
