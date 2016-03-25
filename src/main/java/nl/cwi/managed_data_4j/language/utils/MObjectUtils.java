package nl.cwi.managed_data_4j.language.utils;

import nl.cwi.managed_data_4j.language.managed_object.MObject;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.MObjectField;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.many.MObjectFieldMany;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.single.MObjectFieldPrimitive;
import nl.cwi.managed_data_4j.language.managed_object.managed_object_field.single.MObjectFieldSingle;
import nl.cwi.managed_data_4j.language.schema.models.definition.Field;
import nl.cwi.managed_data_4j.language.schema.models.definition.Klass;
import org.apache.log4j.LogManager;

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

        // primitive leaf, just compare values
        if (PrimitiveUtils.isPrimitiveClass(x.getClass()) &&
                PrimitiveUtils.isPrimitiveClass(y.getClass()) &&
                x.getClass().equals(y.getClass()))
        {
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

        // MObjects leaf
        MObject mObjectX = null;
        MObject mObjectY = null;
        if (Proxy.isProxyClass(x.getClass()) && Proxy.isProxyClass(y.getClass())) {
            mObjectX = (MObject) Proxy.getInvocationHandler(x);
            mObjectY = (MObject) Proxy.getInvocationHandler(y);
        } else {
            mObjectX = ((MObject) x);
            mObjectY = ((MObject) y);
        }
        logger.debug(" <<MObject>> : " + mObjectX.schemaKlass().name());
        List<MObjectField> xFields = extractFields(mObjectX);
        List<MObjectField> yFields = extractFields(mObjectY);

        // sort fields by name
        Collections.sort(xFields, (o1, o2) -> o1.getField().name().compareTo(o2.getField().name()));
        Collections.sort(yFields, (o1, o2) -> o1.getField().name().compareTo(o2.getField().name()));

        return xFields.size() == yFields.size() && // they should have the same size (branch number)
                (xFields.size() == 0 || areFieldsEqual(ht, xFields, yFields, 0)); // if the len is 0 then true, otherwise compare
    }

    private static boolean areVectorsEqual(Map<Object, Object> ht, List<Object> xVector, List<Object> yVector, int n) {
        return ((xVector.size() == yVector.size()) && xVector.size() == n) ||
                e(ht, xVector.get(n), yVector.get(n)) && areVectorsEqual(ht, xVector, yVector, n + 1);
    }

    private static boolean areFieldsEqual(
            Map<Object, Object> ht,
            List<MObjectField> xFields,
            List<MObjectField> yFields,
            int n)
    {
        if (xFields.size() == n && xFields.size() == yFields.size()) {
            return true;
        } else {
            final MObjectField xField = xFields.get(n);
            final MObjectField yField = yFields.get(n);
            System.out.print(" - Field name: " + xFields.get(n).getField().name());

            // FIXME: Union find
            // Check Contain only for non primitives
            // So, if not primitive and not in Spine tree, just skip
            if (!(xField instanceof MObjectFieldPrimitive) && !xField.getField().contain()) {
                System.out.print(" [Cross-Reference] \n"); // Cross reference
                return areFieldsEqual(ht, xFields, yFields, n + 1);
            }
            System.out.print("\n"); // Spine

            return e(ht, xField.get(), yField.get()) && areFieldsEqual(ht, xFields, yFields, n + 1);
        }
    }

    /**
     * Extracts the fields from an input Managed Object and returns a map of them.
     *
     * @param mObject the Managed Object
     * @return the fields
     */
    private static List<MObjectField> extractFields(MObject mObject) {
        return mObject.schemaKlass().fields().stream()
            .map(field -> mObject.getMObjectField(field.name()))
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
