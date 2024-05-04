package com.freeing.common.support.poi.excle.convertor;

/**
 * 类型转化
 * @param <F> 原类型
 * @param <T> 目标类型
 */
public interface Convertor<F, T> {

    T convert(F from);
}
