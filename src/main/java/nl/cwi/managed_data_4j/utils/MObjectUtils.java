package nl.cwi.managed_data_4j.utils;

import nl.cwi.managed_data_4j.managed_object.MObject;
import nl.cwi.managed_data_4j.schema.models.definition.Klass;

import java.lang.reflect.Proxy;

public class MObjectUtils {

    // TODO:
    public static boolean isEqual(Object object1, Object object2) {
        final MObject mObject1 = (MObject) Proxy.getInvocationHandler(object1);
        final MObject mObject2 = (MObject) Proxy.getInvocationHandler(object2);

        final Klass mObject1SchemaKlass = mObject1.schemaKlass();
        final Klass mObject2SchemaKlass = mObject2.schemaKlass();

        return false;
    }
}
