package com.freeing.common.support.parsing;

public interface TokenHandler {
    /**
     * 处理， 如：#{content}
     *
     * @param content
     * @return
     */
    String handleToken(String content);
}
