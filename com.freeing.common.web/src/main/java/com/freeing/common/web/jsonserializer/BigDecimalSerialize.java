package com.freeing.common.web.jsonserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * BigDecimal 普通数字字符串的序列化（非科学计数法）
 * 使用: 属性上添加 @JsonSerialize(using = BigDecimalSerialize.class)
 *
 * @author yanggy
 */
public class BigDecimalSerialize extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
        if (Objects.nonNull(value)) {
            // stripTrailingZeros 去重尾部的 0，toPlainString 转换为 string 并保证不是科学计数法
            jsonGenerator.writeString(value.stripTrailingZeros().toPlainString());
        } else {
            jsonGenerator.writeNull();
        }
    }
}
