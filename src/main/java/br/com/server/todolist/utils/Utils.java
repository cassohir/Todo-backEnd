package br.com.server.todolist.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Utils {
    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);

        PropertyDescriptor[] properties = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor property : properties){
            String propertyName = property.getName();
            Object sourceValue = src.getPropertyValue(propertyName);
            if(sourceValue == null) emptyNames.add(propertyName);
        }
        String[] nullProperties = new String[emptyNames.size()];
        return emptyNames.toArray(nullProperties);
    }

}
