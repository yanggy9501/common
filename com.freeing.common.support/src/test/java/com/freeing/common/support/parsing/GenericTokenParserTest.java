package com.freeing.common.support.parsing;

public class GenericTokenParserTest {
    public static void main(String[] args) {
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", content -> {
            System.out.println(content);
            return "hello " + content;
        });

        genericTokenParser.parse("hello \\#{aaa}, #{bbb}");
    }
}
