package com.freeing.common.support.parsing;

/**
 * 解析器：如 openToken=#{，closeToken=}
 */
public class GenericTokenParser {

    private final String openToken;
    private final String closeToken;
    private final TokenHandler handler;

    public GenericTokenParser(String openToken, String closeToken, TokenHandler handler) {
        this.openToken = openToken;
        this.closeToken = closeToken;
        this.handler = handler;
    }

    /**
     * 解析给定的文本，替换特定的开放和关闭标记内的内容。
     *
     * @param text 需要解析的原始文本字符串。
     * @return 替换标记后生成的字符串。
     */
    public String parse(String text) {
        // 检查输入文本是否为空，若为空则直接返回空字符串
        if (text == null || text.isEmpty()) {
            return "";
        }
        // 寻找开放标记的起始位置
        int start = text.indexOf(openToken);
        // 如果没有找到开放标记，则直接返回原始文本
        if (start == -1) {
            return text;
        }

        char[] src = text.toCharArray();
        // 每次处理的开始位置
        int offset = 0;
        final StringBuilder builder = new StringBuilder();
        // openToken 到 closeToken 之间的内容
        StringBuilder expression = null;
        // 循环处理直到所有开放标记都被处理完毕
        while (start > -1) {
            // 处理转义的开放标记，开发标记是内容的一部分
            if (start > 0 && src[start - 1] == '\\') {
                builder.append(src, offset, start - offset - 1).append(openToken);
                offset = start + openToken.length();
            }
            else {
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }

                // 找到有效的开放标记，开始寻找对应的关闭标记
                builder.append(src, offset, start - offset);
                offset = start + openToken.length();
                int end = text.indexOf(closeToken, offset);

                // 循环处理直到找到有效的关闭标记
                while (end > -1) {
                    // 处理转义的关闭标记，关闭标记是内容的一部分
                    if (end > offset && src[end - 1] == '\\') {
                        expression.append(src, offset, end - offset - 1).append(closeToken);
                        offset = end + closeToken.length();
                        end = text.indexOf(closeToken, offset);
                    } else {
                        // 有效的标记区间，进行替换处理
                        expression.append(src, offset, end - offset);
                        break;
                    }
                }

                if (end == -1) {
                    // 如果没有找到对应的关闭标记，则将剩余部分添加到结果中
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    // 使用类型处理器处理标记内的内容，并将其添加到结果中
                    builder.append(handler.handleToken(expression.toString()));
                    offset = end + closeToken.length();
                }
            }
            // 继续寻找下一个开放标记
            start = text.indexOf(openToken, offset);
        }
        // 将剩余未处理的部分添加到结果中
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

}
