package com.flowiee.pms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LogUtils {
    private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

    public static Map<String, Object[]> logChanges(Object oldObject, Object newObject) {
        Map<String, Object[]> changes = new HashMap<>();
        for (Field field : oldObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object oldValue = field.get(oldObject);
                Object newValue = field.get(newObject);
                if (!Objects.equals(oldValue, newValue)) {
                    changes.put(field.getName(), new Object[]{oldValue, newValue});
                }
            } catch (IllegalAccessException e) {
                logger.error("Error when checking entity's fields changes {}", oldObject.getClass().getName(), e);
            }
        }
        return changes;
    }

    public static String[] getValueChanges(Map<String, Object[]> dataChanges) {
        StringBuilder oldValue = new StringBuilder("Fields: ");
        StringBuilder newValue = new StringBuilder("Fields: ");
        for (Map.Entry<String, Object[]> entry : dataChanges.entrySet()) {
            String field = entry.getKey();
            oldValue.append(field).append(" (").append(entry.getValue()[0].toString()).append("); ");
            newValue.append(field).append(" (").append(entry.getValue()[1].toString()).append("); ");
        }
        String oldValueStr = oldValue.toString();
        String newValueStr = newValue.toString();
        if (oldValueStr.endsWith("; ")) {
            oldValueStr = oldValueStr.substring(0, oldValueStr.length() - 2);
        }
        if (newValueStr.endsWith("; ")) {
            newValueStr = newValueStr.substring(0, newValueStr.length() - 2);
        }
        return new String[]{oldValueStr, newValueStr};
    }
}