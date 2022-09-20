package com.freeing.common.propertycopy.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 对象属性拷贝工具
 *
 * @author yanggy
 */
public class ObjectCopyUtils {
    private final PropertyMapper PROPERTY_MAPPER = new PropertyMapper();

    /**
     *  属性映射
     *  映射规则：属性名相同，属性类型相同
     *
     * @param data 源数据对象
     * @param toClass 映射目标对象的字节码对象
     * @param <E> 目标对象泛型
     * @param <T> 源数据对象泛型
     * @return 目标类型对象
     */
    public <E, T> E map(T data, Class<E> toClass) {
        return PROPERTY_MAPPER.map(data, toClass);
    }

    /**
     *  自定义属性映射
     *  映射规则：属性名可以不同，对应属性类型相同
     *
     * @param data 源数据对象
     * @param toClass 映射目标对象的字节码对象
     * @param <E> 目标对象泛型
     * @param <T> 源数据对象泛型
     * @return 目标类型对象
     */
    public <E, T> E map(T data, Class<E> toClass, Map<String, String> fieldMapConfig) {
        return PROPERTY_MAPPER.map(data, toClass, fieldMapConfig);
    }

    /**
     * 映射集合(默认字段）
     * 映射规则：属性名相同，属性类型相同
     *
     * @param data 源数据对象集合
     * @param toClass 映射目标对象的字节码对象
     * @param <E> 目标对象泛型
     * @param <T> 源数据对象泛型
     * @return List<target>
     */
    public <E, T> List<E> mapAsList(Collection<T> data, Class<E> toClass) {
        return PROPERTY_MAPPER.mapAsList(data, toClass);
    }

    /**
     * 映射集合(默认字段）
     * 映射规则：属性名相同，属性类型相同
     *
     * @param data 源数据对象集合
     * @param toClass 映射目标对象的字节码对象
     * @param <E> 目标对象泛型
     * @param <T> 源数据对象泛型
     * @return List<target>
     */
    public <E, T> List<E> mapAsList(Collection<T> data, Class<E> toClass, Map<String, String> fieldMapConfig) {
        return PROPERTY_MAPPER.mapAsList(data, toClass, fieldMapConfig);
    }
}
