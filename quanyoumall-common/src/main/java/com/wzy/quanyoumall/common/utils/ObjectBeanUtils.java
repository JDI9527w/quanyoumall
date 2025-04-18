package com.wzy.quanyoumall.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.*;

public class ObjectBeanUtils {
    /**
     * 批量copy同属性对象.
     *
     * @param source      源数据
     * @param targetClass 目标类对象
     * @param <T>         源类
     * @param <E>         目标类
     * @return
     */
    public static <T, E> List<E> cpProperties(List<T> source, Class<E> targetClass) {
        List<E> eList = new ArrayList<>();
        source.forEach(item -> {
            try {
                E e = targetClass.newInstance();
                BeanUtils.copyProperties(item, e);
                eList.add(e);
            } catch (InstantiationException instantiationException) {
                instantiationException.printStackTrace();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
        });
        return eList;
    }

    /**
     * 从 Map 中提取 JSON 字符串并反序列化为 List
     *
     * @param responseMap
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> extractAndDeserializeListFromJson(Map<String, Object> responseMap,
                                                                String key,
                                                                Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = objectMapper.writeValueAsString(responseMap.get(key));
        if (data == null) {
            throw new IllegalArgumentException("Key '" + key + "' not found in response map or its value is not a JSON string.");
        }
        return objectMapper.readValue(data, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    /**
     * 提取对象中值为null的字段名
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
