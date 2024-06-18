package com.flowiee.pms.utils;

import com.flowiee.pms.model.ChangeLog;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

public class LogUtils {
    private static final Logger logger = LoggerFactory.getLogger(LogUtils.class);

    public static Map<String, Object[]> logChanges(Object oldObject, Object newObject) {
        Map<String, Object[]> changes = new HashMap<>();
        for (Field field : oldObject.getClass().getDeclaredFields()) {
            if (Collection.class.isAssignableFrom(field.getType())) {
                continue;
            }
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

    public static ChangeLog logChanges2(Object oldObject, Object newObject) {
        ChangeLog changeLog = new ChangeLog();
        Map<String, Object[]> changes = new HashMap<>();
        for (Field field : oldObject.getClass().getDeclaredFields()) {
            if (Collection.class.isAssignableFrom(field.getType())) {
                continue;
            }
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
        changeLog.setLogChanges(changes);
        return changeLog;
    }

    public static String[] getValueChanges(Map<String, Object[]> dataChanges) {
        StringBuilder oldValue = new StringBuilder("Fields: ");
        StringBuilder newValue = new StringBuilder("Fields: ");
        for (Map.Entry<String, Object[]> entry : dataChanges.entrySet()) {
            String field = entry.getKey();
            String oldValueStr = ObjectUtils.isNotEmpty(entry.getValue()[0]) ? entry.getValue()[0].toString() : " ";
            String newValueStr = ObjectUtils.isNotEmpty(entry.getValue()[1]) ? entry.getValue()[1].toString() : " ";
            oldValue.append(field).append(" (").append(oldValueStr).append("); ");
            newValue.append(field).append(" (").append(newValueStr).append("); ");
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