package com.wzy.quanyoumall.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

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
}
