package com.freeing.common.xss.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.freeing.common.xss.util.XssUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 过滤跨站脚本的反序列化工具
 */
public class XssStringJsonDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        if (jp.hasToken(JsonToken.VALUE_STRING)) {
            String value = jp.getValueAsString();

            if (value == null || "".equals(value)) {
                return value;
            }

            List<String> list = new ArrayList<>();
            list.add("<script>");
            list.add("</script>");
            list.add("<iframe>");
            list.add("</iframe>");
            list.add("<noscript>");
            list.add("</noscript>");
            list.add("<frameset>");
            list.add("</frameset>");
            list.add("<frame>");
            list.add("</frame>");
            list.add("<noframes>");
            list.add("</noframes>");
            boolean flag = list.stream().anyMatch(value::contains);
            if (flag) {
                return XssUtils.xssClean(value, null);
            }
            return value;
        }
        return null;
    }
}
