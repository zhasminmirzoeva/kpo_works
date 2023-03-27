package org.example;

import org.example.annotations.Published;
import org.json.JSONObject;
import org.reflections.ReflectionUtils;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class JsonSerializer<T> {
    private Set<Field> publishedFields =  new HashSet<>();

    public JsonSerializer(Class<T> serializedClass) {
        for (Field field: ReflectionUtils.getAllFields(serializedClass)) {
            if (field.getAnnotation(Published.class) != null) {
                publishedFields.add(field);
            }
        }
    }
    public JSONObject serialize(T o) throws IllegalAccessException {
        JSONObject result = new JSONObject();
        for (Field field: publishedFields) {
            field.setAccessible(true);
            result.put(field.getName(), field.get(o));
        }
        return result;
    }
}