package com.flowiee.pms.model;

import com.flowiee.pms.utils.LogUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
public class ChangeLog {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

   private Map<String, Object[]> logChanges;

   public ChangeLog(Object oldObject, Object newObject) {
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
       this.logChanges = changes;
   }

   public String getOldValue() {
       StringBuilder oldValue = new StringBuilder("Fields: ");
       for (Map.Entry<String, Object[]> entry : logChanges.entrySet()) {
           String field = entry.getKey();
           String oldValueStr = ObjectUtils.isNotEmpty(entry.getValue()[0]) ? entry.getValue()[0].toString() : " ";
           oldValue.append(field).append(" (").append(oldValueStr).append("); ");
       }
       String oldValueStr = oldValue.toString();
       if (oldValueStr.endsWith("; ")) {
           oldValueStr = oldValueStr.substring(0, oldValueStr.length() - 2);
       }
       return oldValueStr;
   }

    public String getNewValue() {
        StringBuilder newValue = new StringBuilder("Fields: ");
        for (Map.Entry<String, Object[]> entry : this.logChanges.entrySet()) {
            String field = entry.getKey();
            String newValueStr = ObjectUtils.isNotEmpty(entry.getValue()[1]) ? entry.getValue()[1].toString() : " ";
            newValue.append(field).append(" (").append(newValueStr).append("); ");
        }
        String newValueStr = newValue.toString();
        if (newValueStr.endsWith("; ")) {
            newValueStr = newValueStr.substring(0, newValueStr.length() - 2);
        }
        return newValueStr;
    }
}