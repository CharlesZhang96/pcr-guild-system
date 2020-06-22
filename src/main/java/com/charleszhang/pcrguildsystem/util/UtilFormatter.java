package com.charleszhang.pcrguildsystem.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author Charles Zhang
 */
public class UtilFormatter {

    /**
     * Judge if the object is null
     *
     * @param object object
     * @return return true if object is null, false if object is not null
     */
    public static boolean isNullObject(Object object) {
        // Get the class of object
        Class<?> clazz = object.getClass();
        // Get all attributes
        Field[] fields = clazz.getDeclaredFields();
        // Define result, default value is true
        boolean flag = true;
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                // Get attribute value
                fieldValue = field.get(object);
                // Get attribute type
                Type fieldType = field.getGenericType();
                // Get attribute name
                String fieldName = field.getName();
                System.out.println("Type: " + fieldType + ", Name: " + fieldName + ", Value: " + fieldValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

            // Return false even object has one non-null value
            if (fieldValue != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
