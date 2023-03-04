package com.room4me.utils;

import java.lang.reflect.Field;

import com.room4me.errors.ServerException;

public class ObjectPropsInjector {
    public static<T> void injectFromAnotherObject(
        T objectToInject, T objectToCopy
    ) {
        try {
            Class<?> objectClass = objectToInject.getClass();
            Field[] fields = objectClass.getDeclaredFields();
    
            for (Field field : fields) {
                field.setAccessible(true);
    
                Object originalValue = field.get(objectToInject);
                if(originalValue != null) continue;
    
                Object injectValue = field.get(objectToCopy);
                field.set(objectToInject, injectValue);
            }
        } catch (Exception exception) {
            throw ServerException.INTERNAL_SERVER_EXCEPTION;
        }
    }
}
