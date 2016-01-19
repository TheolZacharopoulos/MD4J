package nl.cwi.managed_data_4j.utils;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.MObjectField;
import nl.cwi.managed_data_4j.managed_object.managed_object_field.MObjectFieldPrimitive;

import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;

public class MObjectUtils {

    // TODO: use checkedAlready, instead of check only @Contain.
    public static boolean isEqual(Object x, Object y) {

        if (!Proxy.isProxyClass(x.getClass()) || !Proxy.isProxyClass(x.getClass())) {
            throw new RuntimeException("Should check Managed Objects only.");
        }

        Set<Object> checkedAlready = new LinkedHashSet<>();
        return equal(checkedAlready, x, y);
    }

    /**
     * The algorithm traverses the nodes of the graph, marking equivalent any two nodes reached by the algorithm
     * if they have the same surface structure, then recurring on the sub-graphs directly reachable from the two nodes.
     * The equality check fails if two nodes reached during this process have different structure or if two leaves differ.
     * If a cycle exists in an equal structure, the algorithm sees that they have already been marked equivalent
     * and does not descend again into the sub graphs, thus assuring termination.
     * A recursive equality check.
     */
    private static boolean equal(Set<Object> checkedAlready, Object obj1, Object obj2) {

        // primitives, just compare values
        if (isPrimitiveAndNoArray(obj1) && isPrimitiveAndNoArray(obj2)) {
            System.out.println(" *** Primitives: obj1 = " + obj1 + ", obj2 = " + obj2);
            return obj1.equals(obj2);
        }

        // vectors
        if (ArrayUtils.isMany(obj1.getClass()) && ArrayUtils.isMany(obj2.getClass())) {
            System.out.println(" <<Vector>>");
            final List<Object> xVector = new LinkedList<>((Collection<Object>) obj1);
            final List<Object> yVector = new LinkedList<>((Collection<Object>) obj2);
            final int xLen = xVector.size();
            final int yLen = yVector.size();

            if (xLen != yLen) return false;
            if (xLen == 0) return true;

            return areVectorsEqual(checkedAlready, xVector, yVector, 0);
        }

        // MObjects
        MObject mObject1 = null;
        MObject mObject2 = null;
        if (Proxy.isProxyClass(obj1.getClass()) && Proxy.isProxyClass(obj1.getClass())) {
            mObject1 = (MObject) Proxy.getInvocationHandler(obj1);
            mObject2 = (MObject) Proxy.getInvocationHandler(obj2);
        } else {
            mObject1 = ((MObject)obj1);
            mObject2 = ((MObject)obj2);
        }
        System.out.println(" <<MObject>> : " + mObject1.schemaKlass().name());
        List<MObjectField> xFields = extractFields(mObject1);
        List<MObjectField> yFields = extractFields(mObject2);
        Collections.sort(xFields, (o1, o2) -> o1.getField().name().compareTo(o2.getField().name()));
        Collections.sort(yFields, (o1, o2) -> o1.getField().name().compareTo(o2.getField().name()));

        if (xFields.size() != yFields.size()) return false;
        if (xFields.size() == 0) return true;

        return areFieldsEqual(checkedAlready, xFields, yFields, 0);
    }

    private static boolean areVectorsEqual(
        Set<Object> checkedAlready,
        List<Object> xVector,
        List<Object> yVector, int n)
    {
        if (xVector.size() == n && xVector.size() == yVector.size()) {
            return true;
        } else {
            System.out.println("- Vector value: " + xVector.get(n));
            if (equal(null, xVector.get(n), yVector.get(n))) {
                return areVectorsEqual(checkedAlready, xVector, yVector, n + 1);
            }
            else {
                return false;
            }
        }
    }

    private static boolean areFieldsEqual(
            Set<Object> checkedAlready,
            List<MObjectField> xFields,
            List<MObjectField> yFields,
            int n)
    {
        if (xFields.size() == n && xFields.size() == yFields.size()) {
            return true;
        } else {
            final MObjectField xField = xFields.get(n);
            final MObjectField yField = yFields.get(n);
            System.out.println("- Field: " + xFields.get(n).getField().name());

            // TODO: Check this (Remove this and keep track on what is visited?)
            // Check Contain only for non primitives
            // So, if not primitive and not in Spine tree, just skip
            if (!(xField instanceof MObjectFieldPrimitive) && !xField.getField().contain()) {
                return areFieldsEqual(checkedAlready, xFields, yFields, n + 1);
            }

            // If the fields are null just continue
            if (xField.get() == null && yField.get() == null) {
                return areFieldsEqual(checkedAlready, xFields, yFields, n + 1);
            }

            if (equal(null, xField.get(), yField.get())) {
                return areFieldsEqual(checkedAlready, xFields, yFields, n + 1);
            }
            else {
                return false;
            }
        }
    }

    /**
     * Extracts the fields from an input Managed Object and returns a map of them.
     * @param mObject the Managed Object
     * @return the fields
     */
    private static List<MObjectField> extractFields(MObject mObject) {
        return mObject.schemaKlass().fields().stream()
            .map(field -> mObject.getMObjectField(field.name()))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private static boolean isPrimitiveAndNoArray(Object object) {
        return (PrimitiveUtils.isPrimitiveClass(object.getClass()) &&
            !ArrayUtils.isMany(object.getClass()));
    }
}
