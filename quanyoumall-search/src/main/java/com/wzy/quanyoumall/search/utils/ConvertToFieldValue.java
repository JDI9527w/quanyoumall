package com.wzy.quanyoumall.search.utils;

import co.elastic.clients.elasticsearch._types.FieldValue;

import java.util.ArrayList;
import java.util.List;

public class ConvertToFieldValue {

    public static <T> List<FieldValue> convertWithList(List<T> list) {
        List<FieldValue> fieldValueList = new ArrayList<>();
        if (list.size() > 0) {
            Class<?> paramClazz = list.get(0).getClass();
            if (paramClazz.isInstance(FieldValue.Kind.Long.getClass())) {
                for (T t : list) {
                    FieldValue fv = FieldValue.of((Long) t);
                    fieldValueList.add(fv);
                }
            }
            if (paramClazz.isInstance(FieldValue.Kind.Double.getClass())) {
                for (T t : list) {
                    FieldValue fv = FieldValue.of((Double) t);
                    fieldValueList.add(fv);
                }
            }
            if (paramClazz.isInstance(FieldValue.Kind.Boolean.getClass())) {
                for (T t : list) {
                    FieldValue fv = FieldValue.of((Boolean) t);
                    fieldValueList.add(fv);
                }
            }
            if (paramClazz.isInstance(FieldValue.Kind.String.getClass())) {
                for (T t : list) {
                    FieldValue fv = FieldValue.of((String) t);
                    fieldValueList.add(fv);
                }
            }
        }
        return fieldValueList;
    }
}
