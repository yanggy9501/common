package com.freeing.common.support.poi.excle.convertor;

public class DefaultConvertor implements Convertor<Object, Object> {

    @Override
    public Object convert(Object from) {
        return from;
    }
}
