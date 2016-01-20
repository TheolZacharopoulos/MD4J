package nl.cwi.managed_data_4j.schema.load;

import nl.cwi.managed_data_4j.schema.models.definition.Type;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Singleton Class which keeps a cache of types and fields
 * in order not to re-create them on the schema loading process.
 */
public class SchemaLoaderCache {

    private static SchemaLoaderCache instance = null;

    private Map<String, Type> typesCache = new LinkedHashMap<>();

    public static SchemaLoaderCache getInstance() {
        if (instance == null) {
            instance = new SchemaLoaderCache();
        }
        return instance;
    }

    public Type getType(String typeName) {
        if (!typesCache.containsKey(typeName)) {
            return null;
        }
        return typesCache.get(typeName);
    }

    public void addType(String typeName, Type type) {
        typesCache.put(typeName, type);
    }

    public Collection<Type> getAllTypes() {
        return typesCache.values();
    }

    public void clean() {
        typesCache.clear();
    }
}
