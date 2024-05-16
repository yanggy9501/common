package com.freeing.common.support.poi.excle.convertor;

import java.util.Map;

public class MappingConvertor implements Convertor<Object, Object> {

    private final Map<Object, Object> mapping;

    public MappingConvertor(Map<Object, Object> mapping) {
        this.mapping = mapping;
    }

    @Override
    public Object convert(Object from) {
        if (mapping.containsKey(from)) {
            return mapping.get(from);
        }
        return from;
    }
}
